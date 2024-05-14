package jp.co.yumemi.android.code_check.feature.repo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.ui.AsyncLoadContents
import jp.co.yumemi.android.code_check.feature.repo.components.RepositoryDetailReadMeSection
import jp.co.yumemi.android.code_check.feature.repo.components.RepositoryDetailTopAppBar
import jp.co.yumemi.android.code_check.feature.repo.components.RepositoryDetailTopSection
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun RepositoryDetailRoute(
    repositoryName: GhRepositoryName,
    navigateToWeb: (String) -> Unit,
    terminate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RepositoryDetailViewModel = koinViewModel(),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(repositoryName) {
        if (screenState !is ScreenState.Idle) {
            viewModel.fetch(repositoryName)
        }
    }

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
        retryAction = { viewModel.fetch(repositoryName) },
    ) {
        RepositoryDetailScreen(
            modifier = Modifier.fillMaxSize(),
            repositoryDetail = it.repositoryDetail,
            readMeHtml = it.readMeHtml,
            language = it.mainLanguage,
            isFavorite = it.isFavorite,
            onClickBack = terminate,
            onClickWeb = navigateToWeb,
            onClickAddFavorite = viewModel::addFavorite,
            onClickRemoveFavorite = viewModel::removeFavorite,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoryDetailScreen(
    repositoryDetail: GhRepositoryDetail,
    readMeHtml: String,
    language: String?,
    isFavorite: Boolean,
    onClickBack: () -> Unit,
    onClickWeb: (String) -> Unit,
    onClickAddFavorite: (GhRepositoryName) -> Unit,
    onClickRemoveFavorite: (GhRepositoryName) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberTopAppBarState()
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(state)

    Scaffold(
        modifier = modifier.nestedScroll(behavior.nestedScrollConnection),
        topBar = {
            RepositoryDetailTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                isFavorite = isFavorite,
                scrollBehavior = behavior,
                onClickBack = onClickBack,
                onClickWeb =  { onClickWeb.invoke("https://github.com/${repositoryDetail.repoName}") },
                onClickAddFavorite = { onClickAddFavorite.invoke(repositoryDetail.repoName) },
                onClickRemoveFavorite = { onClickRemoveFavorite.invoke(repositoryDetail.repoName) },
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = it,
        ) {
            item {
                RepositoryDetailTopSection(
                    modifier = Modifier.fillMaxWidth(),
                    repositoryDetail = repositoryDetail,
                    language = language,
                )
            }

            item {
                RepositoryDetailReadMeSection(
                    modifier = Modifier.fillMaxWidth(),
                    readMeHtml = readMeHtml,
                )
            }
        }
    }
}
