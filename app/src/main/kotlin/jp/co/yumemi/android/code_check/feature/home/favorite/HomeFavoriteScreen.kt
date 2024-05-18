package jp.co.yumemi.android.code_check.feature.home.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.ui.AsyncLoadContents
import jp.co.yumemi.android.code_check.core.ui.components.EmptyView
import jp.co.yumemi.android.code_check.feature.home.favorite.components.HomeFavoriteIdleSection
import jp.co.yumemi.android.code_check.feature.home.favorite.components.HomeFavoriteTopAppBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import me.matsumo.yumemi.codecheck.R
import org.koin.androidx.compose.koinViewModel

@Suppress("detekt.all")
@Composable
fun HomeFavoriteRoute(
    openDrawer: () -> Unit,
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeFavoriteViewModel = koinViewModel(),
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
        HomeFavoriteScreen(
            modifier = Modifier.fillMaxSize(),
            favoriteRepositories = it.favoriteRepositories.toImmutableList(),
            favoriteRepoNames = it.favoriteRepoNames.toImmutableList(),
            languageColors = it.languageColors.toImmutableMap(),
            onRequestRefresh = viewModel::fetch,
            onClickDrawer = openDrawer,
            onClickRepository = navigateToRepositoryDetail,
            onClickAddFavorite = viewModel::addFavorite,
            onClickRemoveFavorite = viewModel::removeFavorite,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeFavoriteScreen(
    favoriteRepositories: ImmutableList<GhRepositoryDetail>,
    favoriteRepoNames: ImmutableList<GhRepositoryName>,
    languageColors: ImmutableMap<String, Color?>,
    onRequestRefresh: () -> Unit,
    onClickDrawer: () -> Unit,
    onClickRepository: (GhRepositoryName) -> Unit,
    onClickAddFavorite: (GhRepositoryName) -> Unit,
    onClickRemoveFavorite: (GhRepositoryName) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val refreshState = rememberPullToRefreshState()

    if (refreshState.isRefreshing) {
        LaunchedEffect(true) {
            onRequestRefresh.invoke()
            refreshState.endRefresh()
        }
    }

    Scaffold(
        modifier = modifier
            .nestedScroll(refreshState.nestedScrollConnection)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeFavoriteTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                scrollBehavior = scrollBehavior,
                onClickDrawer = onClickDrawer,
            )
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (favoriteRepositories.isNotEmpty()) {
                HomeFavoriteIdleSection(
                    modifier = Modifier.fillMaxSize(),
                    favoriteRepositories = favoriteRepositories,
                    favoriteRepoNames = favoriteRepoNames,
                    languageColors = languageColors,
                    contentPadding = it,
                    onClickRepository = onClickRepository,
                    onClickAddFavorite = onClickAddFavorite,
                    onClickRemoveFavorite = onClickRemoveFavorite,
                )
            } else {
                EmptyView(
                    modifier = Modifier.fillMaxSize(),
                    titleRes = R.string.favorite_empty_title,
                    messageRes = R.string.favorite_empty_message,
                )
            }

            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = refreshState,
                contentColor = MaterialTheme.colorScheme.primary,
            )
        }
    }
}
