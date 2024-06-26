package jp.co.yumemi.android.code_check.core.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fleeksoft.ksoup.Ksoup
import io.ktor.client.statement.bodyAsText
import jp.co.yumemi.android.code_check.core.common.extensions.convertRelativeUrlsToAbsolute
import jp.co.yumemi.android.code_check.core.common.extensions.parse
import jp.co.yumemi.android.code_check.core.common.extensions.parsePaging
import jp.co.yumemi.android.code_check.core.datastore.GhCacheDataStore
import jp.co.yumemi.android.code_check.core.model.GhLanguage
import jp.co.yumemi.android.code_check.core.model.GhOrder
import jp.co.yumemi.android.code_check.core.model.GhPaging
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhRepositorySort
import jp.co.yumemi.android.code_check.core.model.GhSearchRepositories
import jp.co.yumemi.android.code_check.core.model.GhSearchUsers
import jp.co.yumemi.android.code_check.core.model.GhTrendRepository
import jp.co.yumemi.android.code_check.core.model.GhTrendSince
import jp.co.yumemi.android.code_check.core.model.GhUserDetail
import jp.co.yumemi.android.code_check.core.model.GhUserSort
import jp.co.yumemi.android.code_check.core.model.entity.GhLanguageEntity
import jp.co.yumemi.android.code_check.core.model.entity.GhTrendRepositoryEntity
import jp.co.yumemi.android.code_check.core.model.entity.RepositoryDetailEntity
import jp.co.yumemi.android.code_check.core.model.entity.SearchRepositoriesEntity
import jp.co.yumemi.android.code_check.core.model.entity.SearchUsersEntity
import jp.co.yumemi.android.code_check.core.model.entity.UserDetailEntity
import jp.co.yumemi.android.code_check.core.model.translate
import jp.co.yumemi.android.code_check.core.repository.paging.GhSearchRepositoriesPaging
import jp.co.yumemi.android.code_check.core.repository.paging.GhSearchUsersPaging
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import me.matsumo.yumemi.codecheck.R

interface GhApiRepository {

    fun clearCache()

    // paging
    fun getSearchUsersPaging(query: String, sort: GhUserSort?, order: GhOrder?): Flow<PagingData<GhSearchUsers.Item>>
    fun getSearchRepositoriesPaging(query: String, sort: GhRepositorySort?, order: GhOrder?): Flow<PagingData<GhSearchRepositories.Item>>

    // search
    suspend fun searchUsers(query: String, sort: GhUserSort?, order: GhOrder?, page: Int): GhPaging<GhSearchUsers>
    suspend fun searchRepositories(query: String, sort: GhRepositorySort?, order: GhOrder?, page: Int): GhPaging<GhSearchRepositories>
    suspend fun searchTrendRepositories(since: GhTrendSince, language: GhLanguage?): List<GhTrendRepository>

    // details
    suspend fun getUserDetail(userName: String): GhUserDetail
    suspend fun getRepositoryDetail(repo: GhRepositoryName, isUseMemoryCache: Boolean = false): GhRepositoryDetail
    suspend fun getRepositoryReadMe(repo: GhRepositoryName, defaultBranch: String): String
    suspend fun getRepositoryLanguages(repo: GhRepositoryName): Map<String, Int>
    suspend fun getRepositoryOgImageLink(repo: GhRepositoryName): String

    // others
    suspend fun getLanguages(): List<GhLanguage>
}

class GhApiRepositoryImpl(
    private val context: Context,
    private val ghCacheDataStore: GhCacheDataStore,
    private val client: ApiClient,
    private val ioDispatcher: CoroutineDispatcher,
) : GhApiRepository {

    private val scope = CoroutineScope(SupervisorJob() + ioDispatcher)

    private var cachedLanguageColors: List<GhLanguage>? = null

    override fun clearCache() {
        scope.launch {
            ghCacheDataStore.clear()
        }
    }

    override fun getSearchUsersPaging(
        query: String,
        sort: GhUserSort?,
        order: GhOrder?,
    ): Flow<PagingData<GhSearchUsers.Item>> {
        return Pager(
            config = PagingConfig(pageSize = ApiClient.PER_PAGE),
            initialKey = null,
            pagingSourceFactory = {
                GhSearchUsersPaging(
                    query = query,
                    sort = sort,
                    order = order,
                    ghApiRepository = this,
                )
            },
        )
            .flow
            .cachedIn(scope)
    }

    override fun getSearchRepositoriesPaging(
        query: String,
        sort: GhRepositorySort?,
        order: GhOrder?,
    ): Flow<PagingData<GhSearchRepositories.Item>> {
        return Pager(
            config = PagingConfig(pageSize = ApiClient.PER_PAGE),
            initialKey = null,
            pagingSourceFactory = {
                GhSearchRepositoriesPaging(
                    query = query,
                    sort = sort,
                    order = order,
                    ghApiRepository = this,
                )
            },
        )
            .flow
            .cachedIn(scope)
    }

    override suspend fun searchUsers(
        query: String,
        sort: GhUserSort?,
        order: GhOrder?,
        page: Int,
    ): GhPaging<GhSearchUsers> = withContext(ioDispatcher) {
        val params = mapOf(
            "q" to query,
            "sort" to sort?.value?.lowercase(),
            "order" to order?.value?.lowercase(),
            "page" to page.toString(),
            "per_page" to ApiClient.PER_PAGE.toString(),
        )

        client.get("search/users", params).parsePaging {
            it.parse<SearchUsersEntity>()!!.translate()
        }
    }

    override suspend fun searchRepositories(
        query: String,
        sort: GhRepositorySort?,
        order: GhOrder?,
        page: Int,
    ): GhPaging<GhSearchRepositories> = withContext(ioDispatcher) {
        val params = mapOf(
            "q" to query,
            "sort" to sort?.value?.lowercase(),
            "order" to order?.value?.lowercase(),
            "page" to page.toString(),
            "per_page" to ApiClient.PER_PAGE.toString(),
        )

        client.get("search/repositories", params).parsePaging {
            it.parse<SearchRepositoriesEntity>()!!.translate()
        }
    }

    override suspend fun searchTrendRepositories(since: GhTrendSince, language: GhLanguage?): List<GhTrendRepository> = withContext(ioDispatcher) {
        val params = mapOf(
            "since" to since.value,
            "language" to language?.title,
        )

        client.get("https://api.gitterapp.com/repositories", params).parse<List<GhTrendRepositoryEntity>>()!!.translate()
    }

    override suspend fun getUserDetail(userName: String): GhUserDetail = withContext(ioDispatcher) {
        client.get("users/$userName").parse<UserDetailEntity>()!!.translate().also {
            ghCacheDataStore.addUserCache(it)
        }
    }

    override suspend fun getRepositoryDetail(repo: GhRepositoryName, isUseMemoryCache: Boolean): GhRepositoryDetail = withContext(ioDispatcher) {
        if (isUseMemoryCache) {
            ghCacheDataStore.getRepositoryMemoryCache(repo)?.let { return@withContext it }
        }

        client.get("repos/$repo").parse<RepositoryDetailEntity>()!!.translate().also {
            ghCacheDataStore.addRepositoryCache(it)
        }
    }

    override suspend fun getRepositoryReadMe(repo: GhRepositoryName, defaultBranch: String): String = withContext(ioDispatcher) {
        client.get(
            url = "repos/$repo/readme",
            headers = mapOf("Accept" to "application/vnd.github.html+json"),
        )
            .bodyAsText()
            .translateReadMe(repo, defaultBranch)
    }

    override suspend fun getRepositoryLanguages(repo: GhRepositoryName): Map<String, Int> = withContext(ioDispatcher) {
        client.get("repos/$repo/languages").parse<Map<String, Int>>()!!
    }

    override suspend fun getRepositoryOgImageLink(repo: GhRepositoryName): String = withContext(ioDispatcher) {
        val html = client.get("https://github.com/$repo").bodyAsText()
        val document = Ksoup.parse(html)

        document.select("meta[property=og:image]").first()?.attr("content")!!
    }

    override suspend fun getLanguages(): List<GhLanguage> = withContext(ioDispatcher) {
        cachedLanguageColors ?: context.resources.openRawResource(R.raw.github_colors).use { inputStream ->
            val serializer = ListSerializer(GhLanguageEntity.serializer())
            val entities = Json.decodeFromString(serializer, inputStream.bufferedReader().readText())

            entities.translate()
        }.also {
            cachedLanguageColors = it
        }
    }

    private fun String.translateReadMe(repo: GhRepositoryName, defaultBranch: String): String {
        val baseUrl = "https://raw.githubusercontent.com/$repo/$defaultBranch/"
        return convertRelativeUrlsToAbsolute(this, baseUrl)
    }
}
