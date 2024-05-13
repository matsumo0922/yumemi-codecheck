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
import androidx.compose.ui.graphics.Color
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
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhRepositorySort
import jp.co.yumemi.android.code_check.core.model.GhSearchHistory
import jp.co.yumemi.android.code_check.core.model.GhSearchRepositories
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.ui.AsyncLoadContents
import jp.co.yumemi.android.code_check.core.ui.LazyPagingItemsLoadContents
import jp.co.yumemi.android.code_check.core.ui.component.SimpleAlertDialog
import jp.co.yumemi.android.code_check.feature.home.search.components.HomeSearchIdleSection
import jp.co.yumemi.android.code_check.feature.home.search.components.HomeSearchTopAppBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.flow.Flow
import me.matsumo.yumemi.codecheck.R

@Composable
fun HomeSearchRoute(
    openDrawer: () -> Unit,
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeSearchViewModel = koinViewModel(HomeSearchViewModel::class),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        if (screenState !is ScreenState.Idle) {
            viewModel.fetch()
        }
    }

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
        retryAction = viewModel::fetch,
    ) {
        HomeSearchScreen(
            modifier = Modifier.fillMaxSize(),
            query = it.query,
            suggestions = it.suggestions.toImmutableList(),
            searchHistories = it.searchHistories.toImmutableList(),
            searchRepositoriesPaging = it.searchRepositoriesPaging,
            languageColors = it.languageColors.toImmutableMap(),
            onClickDrawerMenu = openDrawer,
            onClickSearch = viewModel::searchRepositories,
            onClickRemoveSearchHistory = viewModel::removeSearchHistory,
            onClickRepository = navigateToRepositoryDetail,
            onClickAddFavorite = viewModel::addFavorite,
            onClickRemoveFavorite = viewModel::removeFavorite,
            onUpdateQuery = viewModel::updateQuery,
        )
    }
}

@Composable
private fun HomeSearchScreen(
    query: String,
    suggestions: ImmutableList<GhSearchHistory>,
    searchHistories: ImmutableList<GhSearchHistory>,
    searchRepositoriesPaging: Flow<PagingData<GhSearchRepositories.Item>>,
    languageColors: ImmutableMap<String, Color?>,
    onClickDrawerMenu: () -> Unit,
    onClickSearch: (String, GhRepositorySort?, GhOrder?) -> Unit,
    onClickRemoveSearchHistory: (GhSearchHistory) -> Unit,
    onClickRepository: (GhRepositoryName) -> Unit,
    onClickAddFavorite: (GhRepositoryName) -> Unit,
    onClickRemoveFavorite: (GhRepositoryName) -> Unit,
    onUpdateQuery: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val repositoriesPager = searchRepositoriesPaging.collectAsLazyPagingItems()

    var removeSearchHistoryItem = remember<GhSearchHistory?> { null }
    var topAppBarHeight by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier.semantics { isTraversalGroup = true },
    ) {
        HomeSearchTopAppBar(
            modifier = Modifier
                .semantics { traversalIndex = -1f }
                .align(Alignment.TopCenter)
                .onGloballyPositioned {
                    if (topAppBarHeight == 0) topAppBarHeight = it.size.height
                },
            query = query,
            suggestions = suggestions.toImmutableList(),
            searchHistories = searchHistories.toImmutableList(),
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
                query = query,
                languageColors = languageColors.toImmutableMap(),
                pagingAdapter = repositoriesPager,
                contentPadding = PaddingValues(
                    top = with(density) { topAppBarHeight.toDp() + 16.dp },
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp,
                ),
                onClickRepository = onClickRepository,
                onClickAddFavorite = onClickAddFavorite,
                onClickRemoveFavorite = onClickRemoveFavorite,
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
