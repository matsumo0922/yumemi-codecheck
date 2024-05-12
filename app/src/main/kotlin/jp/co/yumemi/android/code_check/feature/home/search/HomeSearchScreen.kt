package jp.co.yumemi.android.code_check.feature.home.search

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import jp.co.yumemi.android.code_check.core.extensions.koinViewModel

@Composable
fun HomeSearchScreen(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ViewModel = koinViewModel(HomeSearchViewModel::class),
) {

}