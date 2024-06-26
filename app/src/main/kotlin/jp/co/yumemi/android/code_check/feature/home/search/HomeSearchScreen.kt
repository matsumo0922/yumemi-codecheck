package jp.co.yumemi.android.code_check.feature.home.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
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
import jp.co.yumemi.android.code_check.core.model.GhLanguage
import jp.co.yumemi.android.code_check.core.model.GhOrder
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhRepositorySort
import jp.co.yumemi.android.code_check.core.model.GhSearchHistory
import jp.co.yumemi.android.code_check.core.model.GhSearchRepositories
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.ui.AsyncLoadContents
import jp.co.yumemi.android.code_check.core.ui.LazyPagingItemsLoadContents
import jp.co.yumemi.android.code_check.core.ui.components.SimpleAlertDialog
import jp.co.yumemi.android.code_check.feature.home.search.components.HomeSearchIdleSection
import jp.co.yumemi.android.code_check.feature.home.search.components.HomeSearchSettingDialog
import jp.co.yumemi.android.code_check.feature.home.search.components.HomeSearchTopAppBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import me.matsumo.yumemi.codecheck.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeSearchRoute(
    openDrawer: () -> Unit,
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeSearchViewModel = koinViewModel(),
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
            favoriteRepoNames = it.favoriteRepoNames.toImmutableList(),
            languages = it.languages.toImmutableList(),
            selectedOrder = it.selectedOrder,
            selectedSort = it.selectedSort,
            onClickDrawerMenu = openDrawer,
            onClickSearch = viewModel::searchRepositories,
            onClickRemoveSearchHistory = viewModel::removeSearchHistory,
            onClickRepository = navigateToRepositoryDetail,
            onClickAddFavorite = viewModel::addFavorite,
            onClickRemoveFavorite = viewModel::removeFavorite,
            onClickUpdateSetting = viewModel::updateSetting,
            onUpdateQuery = viewModel::updateQuery,
        )
    }
}

@Composable
private fun HomeSearchScreen(
    query: String,
    selectedOrder: GhOrder?,
    selectedSort: GhRepositorySort?,
    suggestions: ImmutableList<GhSearchHistory>,
    searchHistories: ImmutableList<GhSearchHistory>,
    searchRepositoriesPaging: Flow<PagingData<GhSearchRepositories.Item>>,
    favoriteRepoNames: ImmutableList<GhRepositoryName>,
    languages: ImmutableList<GhLanguage>,
    onClickDrawerMenu: () -> Unit,
    onClickSearch: (String, GhRepositorySort?, GhOrder?) -> Unit,
    onClickUpdateSetting: (GhOrder?, GhRepositorySort?) -> Unit,
    onClickRemoveSearchHistory: (GhSearchHistory) -> Unit,
    onClickRepository: (GhRepositoryName) -> Unit,
    onClickAddFavorite: (GhRepositoryName) -> Unit,
    onClickRemoveFavorite: (GhRepositoryName) -> Unit,
    onUpdateQuery: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val repositoriesPager = searchRepositoriesPaging.collectAsLazyPagingItems()

    var isDisplayedSetting by remember { mutableStateOf(false) }
    var removeSearchHistoryItem by remember { mutableStateOf<GhSearchHistory?>(null) }
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
            suggestions = suggestions,
            searchHistories = searchHistories,
            onClickDrawerMenu = onClickDrawerMenu,
            onClickSearch = { onClickSearch.invoke(it, selectedSort, selectedOrder) },
            onClickRemoveSearchHistory = { removeSearchHistoryItem = it },
            onClickSort = { isDisplayedSetting = true },
            onUpdateQuery = onUpdateQuery,
        )

        LazyPagingItemsLoadContents(
            modifier = Modifier.fillMaxSize(),
            lazyPagingItems = repositoriesPager,
            emptyMessageRes = R.string.search_empty_message,
        ) {
            HomeSearchIdleSection(
                modifier = Modifier.fillMaxSize(),
                query = query,
                favoriteRepoNames = favoriteRepoNames,
                languages = languages,
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
            onClickNegative = {
                removeSearchHistoryItem = null
            },
            isCaution = true,
        )
    }

    if (isDisplayedSetting) {
        HomeSearchSettingDialog(
            selectedOrder = selectedOrder,
            selectedSort = selectedSort,
            onClickUpdateSetting = onClickUpdateSetting,
            onDismiss = { isDisplayedSetting = false },
        )
    }
}
