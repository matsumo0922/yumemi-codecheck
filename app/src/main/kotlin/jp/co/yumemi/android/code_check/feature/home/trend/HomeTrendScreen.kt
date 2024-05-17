package jp.co.yumemi.android.code_check.feature.home.trend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhTrendRepository
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.ui.AsyncLoadContents
import jp.co.yumemi.android.code_check.core.ui.components.EmptyView
import jp.co.yumemi.android.code_check.feature.home.trend.components.HomeTrendIdleSection
import jp.co.yumemi.android.code_check.feature.home.trend.components.HomeTrendTopAppBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import me.matsumo.yumemi.codecheck.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeTrendRoute(
    openDrawer: () -> Unit,
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeTrendViewModel = koinViewModel(),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        if (screenState !is ScreenState.Idle) {
            viewModel.fetch()
        }
    }

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
        retryAction = viewModel::fetch,
    ) {
        HomeTrendScreen(
            modifier = Modifier.fillMaxSize(),
            trendRepositories = it.trendingRepositories.toImmutableList(),
            favoriteRepoNames = it.favoriteRepoNames.toImmutableList(),
            onRequestRefresh = viewModel::fetch,
            onRequestOgImageLink = viewModel::requestOgImageLink,
            onClickRepository = navigateToRepositoryDetail,
            onClickAddFavorite = viewModel::addFavorite,
            onClickRemoveFavorite = viewModel::removeFavorite,
            onClickDrawer = openDrawer,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTrendScreen(
    trendRepositories: ImmutableList<GhTrendRepository>,
    favoriteRepoNames: ImmutableList<GhRepositoryName>,
    onRequestRefresh: () -> Unit,
    onRequestOgImageLink: suspend (GhRepositoryName) -> String,
    onClickRepository: (GhRepositoryName) -> Unit,
    onClickAddFavorite: (GhRepositoryName) -> Unit,
    onClickRemoveFavorite: (GhRepositoryName) -> Unit,
    onClickDrawer: () -> Unit,
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
            HomeTrendTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                scrollBehavior = scrollBehavior,
                onClickDrawer = onClickDrawer,
            )
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            if (trendRepositories.isNotEmpty()) {
                HomeTrendIdleSection(
                    modifier = Modifier.fillMaxSize(),
                    trendRepositories = trendRepositories,
                    favoriteRepoNames = favoriteRepoNames,
                    contentPadding = it,
                    onRequestOgImageLink = onRequestOgImageLink,
                    onClickRepository = onClickRepository,
                    onClickAddFavorite = onClickAddFavorite,
                    onClickRemoveFavorite = onClickRemoveFavorite,
                )
            } else {
                EmptyView(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    titleRes = R.string.trending_empty_title,
                    messageRes = R.string.trending_empty_message,
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
