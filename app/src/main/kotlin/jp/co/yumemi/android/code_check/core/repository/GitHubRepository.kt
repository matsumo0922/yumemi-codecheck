package jp.co.yumemi.android.code_check.core.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import jp.co.yumemi.android.code_check.core.extensions.parse
import jp.co.yumemi.android.code_check.core.extensions.parsePaging
import jp.co.yumemi.android.code_check.core.model.GhPaging
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.model.GhUserDetail
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

interface GitHubRepository {

    // paging
    fun getSearchUsersPaging(query: String, sort: UserSort?, order: Order?): Flow<PagingData<SearchUsers.Item>>
    fun getSearchRepositoriesPaging(query: String, sort: RepositorySort?, order: Order?): Flow<PagingData<SearchRepositories.Item>>

    // search
    suspend fun searchUsers(query: String, sort: UserSort?, order: Order?, page: Int): GhPaging<SearchUsers>
    suspend fun searchRepositories(query: String, sort: RepositorySort?, order: Order?, page: Int): GhPaging<SearchRepositories>

    // details
    suspend fun getUserDetail(userName: String): GhUserDetail
    suspend fun getRepositoryDetail(owner: String, name: String): GhRepositoryDetail

    // order
    enum class Order(val value: String) {
        ASC("asc"),
        DESC("desc"),
    }

    // sort
    enum class UserSort(val value: String) {
        FOLLOWERS("followers"),
        REPOSITORIES("repositories"),
        JOINED("joined"),
    }

    enum class RepositorySort(val value: String) {
        STARS("stars"),
        FORKS("forks"),
        HELP_WANTED_ISSUES("help-wanted-issues"),
        UPDATED("updated"),
    }
}

class GitHubRepositoryImpl(
    private val client: ApiClient,
    private val ioDispatcher: CoroutineDispatcher,
) : GitHubRepository {

    private val scope = CoroutineScope(SupervisorJob() + ioDispatcher)

    override fun getSearchUsersPaging(
        query: String,
        sort: GitHubRepository.UserSort?,
        order: GitHubRepository.Order?,
    ): Flow<PagingData<SearchUsers.Item>> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            initialKey = null,
            pagingSourceFactory = {
                GhSearchUsersPaging(
                    query = query,
                    sort = sort,
                    order = order,
                    gitHubRepository = this,
                )
            },
        )
            .flow
            .cachedIn(scope)
    }

    override fun getSearchRepositoriesPaging(
        query: String,
        sort: GitHubRepository.RepositorySort?,
        order: GitHubRepository.Order?,
    ): Flow<PagingData<SearchRepositories.Item>> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            initialKey = null,
            pagingSourceFactory = {
                GhSearchRepositoriesPaging(
                    query = query,
                    sort = sort,
                    order = order,
                    gitHubRepository = this,
                )
            },
        )
            .flow
            .cachedIn(scope)
    }

    override suspend fun searchUsers(
        query: String,
        sort: GitHubRepository.UserSort?,
        order: GitHubRepository.Order?,
        page: Int,
    ): GhPaging<SearchUsers> = withContext(ioDispatcher) {
        val params = mapOf(
            "q" to query,
            "sort" to sort?.value,
            "order" to order?.value,
            "page" to page.toString(),
        )

        client.get("search/users", params).parsePaging {
            it.parse<SearchUsersEntity>()!!.translate()
        }
    }

    override suspend fun searchRepositories(
        query: String,
        sort: GitHubRepository.RepositorySort?,
        order: GitHubRepository.Order?,
        page: Int,
    ): GhPaging<SearchRepositories> = withContext(ioDispatcher) {
        val params = mapOf(
            "q" to query,
            "sort" to sort?.value,
            "order" to order?.value,
            "page" to page.toString(),
        )

        client.get("search/repositories", params).parsePaging {
            it.parse<SearchRepositoriesEntity>()!!.translate()
        }
    }

    override suspend fun getUserDetail(userName: String): GhUserDetail = withContext(ioDispatcher) {
        client.get("users/$userName").parse<UserDetailEntity>()!!.translate()
    }

    override suspend fun getRepositoryDetail(owner: String, name: String): GhRepositoryDetail = withContext(ioDispatcher) {
        client.get("repos/$owner/$name").parse<RepositoryDetailEntity>()!!.translate()
    }
}