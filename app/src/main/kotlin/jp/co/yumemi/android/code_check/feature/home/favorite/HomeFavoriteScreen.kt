package jp.co.yumemi.android.code_check.feature.home.favorite

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.co.yumemi.android.code_check.core.extensions.koinViewModel

@Suppress("detekt.all")
@Composable
fun HomeFavoriteRoute(
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeFavoriteViewModel = koinViewModel(HomeFavoriteViewModel::class),
) {
}
