package jp.co.yumemi.android.code_check.feature.repo

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.yumemi.android.code_check.core.extensions.koinViewModel
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.ui.AsyncLoadContents
import jp.co.yumemi.android.code_check.feature.repo.components.RepositoryDetailTopAppBar
import jp.co.yumemi.android.code_check.feature.repo.components.RepositoryDetailTopSection

@Composable
internal fun RepositoryDetailRoute(
    repositoryName: GhRepositoryName,
    modifier: Modifier = Modifier,
    viewModel: RepositoryDetailViewModel = koinViewModel(RepositoryDetailViewModel::class, key = repositoryName.toString()),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(screenState) {
        if (screenState !is ScreenState.Idle) {
            viewModel.fetch(repositoryName)
        }
    }

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
        retryAction = { viewModel.fetch(repositoryName) },
    ) {
        Text(text = it.repositoryDetail.repoName.toString())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RepositoryDetailScreen(
    onClickBack: () -> Unit,
    onClickWeb: () -> Unit,
    onClickShare: () -> Unit,
    repositoryDetail: GhRepositoryDetail,
    modifier: Modifier = Modifier,
) {
    val state = rememberTopAppBarState()
    val behavior = TopAppBarDefaults.pinnedScrollBehavior(state)

    Scaffold(
        modifier = modifier.nestedScroll(behavior.nestedScrollConnection),
        topBar = {
            RepositoryDetailTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                scrollBehavior = behavior,
                onClickBack = onClickBack,
                onClickWeb = onClickWeb,
                onClickShare = onClickShare,
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
                )
            }
        }
    }
}
