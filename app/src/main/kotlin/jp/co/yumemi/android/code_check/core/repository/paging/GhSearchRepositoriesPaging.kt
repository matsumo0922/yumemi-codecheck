package jp.co.yumemi.android.code_check.core.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import jp.co.yumemi.android.code_check.core.common.extensions.suspendRunCatching
import jp.co.yumemi.android.code_check.core.model.GhOrder
import jp.co.yumemi.android.code_check.core.model.GhRepositorySort
import jp.co.yumemi.android.code_check.core.model.GhSearchRepositories
import jp.co.yumemi.android.code_check.core.repository.GhApiRepository

class GhSearchRepositoriesPaging(
    private val query: String,
    private val sort: GhRepositorySort?,
    private val order: GhOrder?,
    private val ghApiRepository: GhApiRepository,
) : PagingSource<Int, GhSearchRepositories.Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GhSearchRepositories.Item> {
        return suspendRunCatching {
            ghApiRepository.searchRepositories(query, sort, order, params.key ?: 1)
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

    override fun getRefreshKey(state: PagingState<Int, GhSearchRepositories.Item>): Int? = null
}
