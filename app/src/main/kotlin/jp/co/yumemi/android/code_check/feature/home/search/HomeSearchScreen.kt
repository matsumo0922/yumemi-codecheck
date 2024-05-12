package jp.co.yumemi.android.code_check.feature.home.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.yumemi.android.code_check.core.extensions.koinViewModel
import jp.co.yumemi.android.code_check.core.ui.AsyncLoadContents

@Composable
fun HomeSearchRoute(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeSearchViewModel = koinViewModel(HomeSearchViewModel::class),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
        retryAction = viewModel::fetch
    ) {

    }
}

@Composable
private fun HomeSearchScreen(
    openDrawer: () -> Unit,
) {

}
