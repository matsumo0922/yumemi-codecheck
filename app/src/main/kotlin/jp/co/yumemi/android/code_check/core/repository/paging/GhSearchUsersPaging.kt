package jp.co.yumemi.android.code_check.core.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import jp.co.yumemi.android.code_check.core.common.extensions.suspendRunCatching
import jp.co.yumemi.android.code_check.core.model.GhOrder
import jp.co.yumemi.android.code_check.core.model.GhSearchUsers
import jp.co.yumemi.android.code_check.core.model.GhUserSort
import jp.co.yumemi.android.code_check.core.repository.GhApiRepository

class GhSearchUsersPaging(
    private val query: String,
    private val sort: GhUserSort?,
    private val order: GhOrder?,
    private val ghApiRepository: GhApiRepository,
) : PagingSource<Int, GhSearchUsers.Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GhSearchUsers.Item> {
        return suspendRunCatching {
            ghApiRepository.searchUsers(query, sort, order, params.key ?: 1)
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

    override fun getRefreshKey(state: PagingState<Int, GhSearchUsers.Item>): Int? = null
}
