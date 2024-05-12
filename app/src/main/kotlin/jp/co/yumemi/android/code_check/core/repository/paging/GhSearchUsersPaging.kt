package jp.co.yumemi.android.code_check.core.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import jp.co.yumemi.android.code_check.core.extensions.suspendRunCatching
import jp.co.yumemi.android.code_check.core.model.SearchUsers
import jp.co.yumemi.android.code_check.core.repository.GitHubRepository

class GhSearchUsersPaging(
    private val query: String,
    private val sort: GitHubRepository.UserSort?,
    private val order: GitHubRepository.Order?,
    private val gitHubRepository: GitHubRepository,
) : PagingSource<Int, SearchUsers.Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchUsers.Item> {
        return suspendRunCatching {
            gitHubRepository.searchUsers(query, sort, order, params.key ?: 1)
        }.fold(
            onSuccess = {
                LoadResult.Page(
                    data = it.data.items,
                    nextKey = it.pagination.next,
                    prevKey = it.pagination.previous,
                )
            },
            onFailure = {
                LoadResult.Error(it)
            },
        )
    }

    override fun getRefreshKey(state: PagingState<Int, SearchUsers.Item>): Int? = null
}