package jp.co.yumemi.android.code_check.feature.repo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.yumemi.android.code_check.core.extensions.koinViewModel
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.ui.AsyncLoadContents

@Composable
internal fun RepositoryDetailRoute(
    repositoryName: GhRepositoryName,
    modifier: Modifier = Modifier,
    viewModel: RepositoryDetailViewModel = koinViewModel(RepositoryDetailViewModel::class),
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

    }
}
