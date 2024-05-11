package jp.co.yumemi.android.code_check.core.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import jp.co.yumemi.android.code_check.core.extensions.suspendRunCatching
import jp.co.yumemi.android.code_check.core.model.SearchRepositories
import jp.co.yumemi.android.code_check.core.repository.GitHubRepository

class GhSearchRepositoriesPaging(
    private val query: String,
    private val sort: GitHubRepository.RepositorySort?,
    private val order: GitHubRepository.Order?,
    private val gitHubRepository: GitHubRepository,
) : PagingSource<Int, SearchRepositories.Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchRepositories.Item> {
        return suspendRunCatching {
            gitHubRepository.searchRepositories(query, sort, order, params.key ?: 1)
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

    override fun getRefreshKey(state: PagingState<Int, SearchRepositories.Item>): Int? = null
}
