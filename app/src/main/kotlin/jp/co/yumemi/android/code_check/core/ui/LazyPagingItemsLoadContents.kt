package jp.co.yumemi.android.code_check.core.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.ui.component.EmptyView
import jp.co.yumemi.android.code_check.core.ui.component.ErrorView
import jp.co.yumemi.android.code_check.core.ui.component.LoadingView
import jp.co.yumemi.android.code_check.core.ui.extensions.isEmpty
import me.matsumo.yumemi.codecheck.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : Any> LazyPagingItemsLoadContents(
    lazyPagingItems: LazyPagingItems<T>,
    modifier: Modifier = Modifier,
    isSwipeEnabled: Boolean = true,
    emptyTitleRes: Int = R.string.error_no_data,
    emptyMessageRes: Int = R.string.error_executed,
    content: @Composable (CombinedLoadStates) -> Unit,
) {
    Surface(modifier) {
        if (lazyPagingItems.isEmpty()) {
            EmptyView(
                titleRes = emptyTitleRes,
                messageRes = emptyMessageRes,
            )
        } else {
            lazyPagingItems.apply {
                AnimatedContent(
                    targetState = loadState.refresh,
                    transitionSpec = { fadeIn().togetherWith(fadeOut()) },
                    contentKey = { it::class },
                    label = "LazyPagingItemsLoadContents",
                ) {
                    when (it) {
                        is LoadState.Loading -> {
                            LoadingView()
                        }

                        is LoadState.Error -> {
                            ErrorView(
                                errorState = ScreenState.Error(R.string.error_no_data),
                                retryAction = { lazyPagingItems.refresh() },
                            )
                        }

                        is LoadState.NotLoading -> {
                            val isRefreshing by remember(lazyPagingItems.loadState) {
                                derivedStateOf { lazyPagingItems.loadState.refresh is LoadState.Loading }
                            }
                            val refreshState = rememberPullRefreshState(
                                refreshing = isRefreshing,
                                onRefresh = { lazyPagingItems.refresh() },
                            )

                            Box(Modifier.pullRefresh(refreshState, isSwipeEnabled)) {
                                content.invoke(loadState)

                                PullRefreshIndicator(
                                    refreshing = isRefreshing,
                                    state = refreshState,
                                    modifier = Modifier.align(Alignment.TopCenter),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
