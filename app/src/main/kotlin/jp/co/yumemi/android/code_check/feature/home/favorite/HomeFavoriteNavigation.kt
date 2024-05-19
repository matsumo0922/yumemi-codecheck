package jp.co.yumemi.android.code_check.feature.home.favorite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName

const val HomeFavoriteRoute = "homeFavorite"

fun NavController.navigateToHomeFavorite(navOptions: NavOptions? = null) {
    this.navigate(HomeFavoriteRoute, navOptions)
}

fun NavGraphBuilder.homeFavoriteScreen(
    openDrawer: () -> Unit,
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
) {
    composable(HomeFavoriteRoute) {
        HomeFavoriteRoute(
            modifier = Modifier.fillMaxSize(),
            openDrawer = openDrawer,
            navigateToRepositoryDetail = navigateToRepositoryDetail,
        )
    }
}
