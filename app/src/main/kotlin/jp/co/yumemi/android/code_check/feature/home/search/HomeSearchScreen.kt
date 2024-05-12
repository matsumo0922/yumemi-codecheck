package jp.co.yumemi.android.code_check.feature.home.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import jp.co.yumemi.android.code_check.core.extensions.koinViewModel
import jp.co.yumemi.android.code_check.core.model.GhOrder
import jp.co.yumemi.android.code_check.core.model.GhRepositorySort
import jp.co.yumemi.android.code_check.core.model.GhSearchHistory
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.model.SearchRepositories
import jp.co.yumemi.android.code_check.core.ui.AsyncLoadContents
import jp.co.yumemi.android.code_check.core.ui.LazyPagingItemsLoadContents
import jp.co.yumemi.android.code_check.core.ui.component.SimpleAlertDialog
import jp.co.yumemi.android.code_check.feature.home.search.components.HomeSearchIdleSection
import jp.co.yumemi.android.code_check.feature.home.search.components.HomeSearchTopAppBar
import kotlinx.coroutines.flow.Flow
import me.matsumo.yumemi.codecheck.R

@Composable
fun HomeSearchRoute(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeSearchViewModel = koinViewModel(HomeSearchViewModel::class),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(screenState) {
        if (screenState !is ScreenState.Idle) {
            viewModel.fetch()
        }
    }

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
        retryAction = viewModel::fetch
    ) {
        HomeSearchScreen(
            modifier = Modifier.fillMaxSize(),
            query = it.query,
            suggestions = it.suggestions,
            searchHistories = it.searchHistories,
            searchRepositoriesPaging = it.searchRepositoriesPaging,
            onClickDrawerMenu = openDrawer,
            onClickSearch = viewModel::searchRepositories,
            onClickRemoveSearchHistory = viewModel::removeSearchHistory,
            onUpdateQuery = viewModel::updateQuery,
        )
    }
}

@Composable
private fun HomeSearchScreen(
    query: String,
    suggestions: List<GhSearchHistory>,
    searchHistories: List<GhSearchHistory>,
    searchRepositoriesPaging: Flow<PagingData<SearchRepositories.Item>>,
    onClickDrawerMenu: () -> Unit,
    onClickSearch: (String, GhRepositorySort?, GhOrder?) -> Unit,
    onClickRemoveSearchHistory: (GhSearchHistory) -> Unit,
    onUpdateQuery: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val repositoriesPager = searchRepositoriesPaging.collectAsLazyPagingItems()

    var removeSearchHistoryItem = remember<GhSearchHistory?> { null }
    var topAppBarHeight by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier.semantics { isTraversalGroup = true }
    ) {
        HomeSearchTopAppBar(
            modifier = Modifier
                .semantics { traversalIndex = -1f }
                .align(Alignment.TopCenter)
                .onGloballyPositioned {
                    if (topAppBarHeight == 0) topAppBarHeight = it.size.height
                },
            query = query,
            suggestions = suggestions,
            searchHistories = searchHistories,
            onClickDrawerMenu = onClickDrawerMenu,
            onClickSearch = { onClickSearch.invoke(it, null, null) },
            onClickRemoveSearchHistory = { removeSearchHistoryItem = it },
            onUpdateQuery = onUpdateQuery,
        )

        LazyPagingItemsLoadContents(
            modifier = Modifier.fillMaxSize(),
            lazyPagingItems = repositoriesPager,
        ) {
            HomeSearchIdleSection(
                modifier = Modifier.fillMaxSize(),
                pagingAdapter = repositoriesPager,
                contentPadding = PaddingValues(
                    top = with(density) { topAppBarHeight.toDp() + 16.dp },
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
            )
        }
    }

    if (removeSearchHistoryItem != null) {
        SimpleAlertDialog(
            title = stringResource(R.string.search_remove_history_title),
            description = stringResource(R.string.search_remove_history_message, removeSearchHistoryItem!!.query),
            positiveText = stringResource(R.string.common_delete),
            negativeText = stringResource(R.string.common_cancel),
            onClickPositive = {
                onClickRemoveSearchHistory.invoke(removeSearchHistoryItem!!)
                removeSearchHistoryItem = null
            },
            onClickNegative = { removeSearchHistoryItem = null },
            isCaution = true,
        )
    }
}
