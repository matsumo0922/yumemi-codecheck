package jp.co.yumemi.android.code_check.core.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import jp.co.yumemi.android.code_check.core.datastore.GhCacheDataStore
import jp.co.yumemi.android.code_check.core.extensions.parse
import jp.co.yumemi.android.code_check.core.extensions.parsePaging
import jp.co.yumemi.android.code_check.core.model.GhOrder
import jp.co.yumemi.android.code_check.core.model.GhPaging
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhRepositorySort
import jp.co.yumemi.android.code_check.core.model.GhUserDetail
import jp.co.yumemi.android.code_check.core.model.GhUserSort
import jp.co.yumemi.android.code_check.core.model.SearchRepositories
import jp.co.yumemi.android.code_check.core.model.SearchUsers
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
import kotlinx.coroutines.withContext

interface GhApiRepository {

    // paging
    fun getSearchUsersPaging(query: String, sort: GhUserSort?, order: GhOrder?): Flow<PagingData<SearchUsers.Item>>
    fun getSearchRepositoriesPaging(query: String, sort: GhRepositorySort?, order: GhOrder?): Flow<PagingData<SearchRepositories.Item>>

    // search
    suspend fun searchUsers(query: String, sort: GhUserSort?, order: GhOrder?, page: Int): GhPaging<SearchUsers>
    suspend fun searchRepositories(query: String, sort: GhRepositorySort?, order: GhOrder?, page: Int): GhPaging<SearchRepositories>

    // details
    suspend fun getUserDetail(userName: String): GhUserDetail
    suspend fun getRepositoryDetail(repo: GhRepositoryName): GhRepositoryDetail
}

class GhApiRepositoryImpl(
    private val ghCacheDataStore: GhCacheDataStore,
    private val client: ApiClient,
    private val ioDispatcher: CoroutineDispatcher,
) : GhApiRepository {

    private val scope = CoroutineScope(SupervisorJob() + ioDispatcher)

    override fun getSearchUsersPaging(
        query: String,
        sort: GhUserSort?,
        order: GhOrder?,
    ): Flow<PagingData<SearchUsers.Item>> {
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
    ): Flow<PagingData<SearchRepositories.Item>> {
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
    ): GhPaging<SearchUsers> = withContext(ioDispatcher) {
        val params = mapOf(
            "q" to query,
            "sort" to sort?.value,
            "order" to order?.value,
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
    ): GhPaging<SearchRepositories> = withContext(ioDispatcher) {
        val params = mapOf(
            "q" to query,
            "sort" to sort?.value,
            "order" to order?.value,
            "page" to page.toString(),
            "per_page" to ApiClient.PER_PAGE.toString(),
        )

        client.get("search/repositories", params).parsePaging {
            it.parse<SearchRepositoriesEntity>()!!.translate()
        }
    }

    override suspend fun getUserDetail(userName: String): GhUserDetail = withContext(ioDispatcher) {
        client.get("users/$userName").parse<UserDetailEntity>()!!.translate().also {
            ghCacheDataStore.addUserCache(it)
        }
    }

    override suspend fun getRepositoryDetail(repo: GhRepositoryName): GhRepositoryDetail = withContext(ioDispatcher) {
        client.get("repos/$repo").parse<RepositoryDetailEntity>()!!.translate().also {
            ghCacheDataStore.addRepositoryCache(it)
        }
    }
}
