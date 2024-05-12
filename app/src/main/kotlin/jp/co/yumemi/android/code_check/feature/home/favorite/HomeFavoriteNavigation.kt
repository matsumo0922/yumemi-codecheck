package jp.co.yumemi.android.code_check.feature.home.favorite

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HomeFavoriteRoute = "homeFavorite"

fun NavController.navigateToHomeFavorite() {
    this.navigate(HomeFavoriteRoute)
}

fun NavGraphBuilder.homeScreenFavorite(
    openDrawer: () -> Unit
) {
    composable(HomeFavoriteRoute) {
        HomeFavoriteRoute(
            modifier = Modifier.fillMaxSize(),
            openDrawer = openDrawer,
        )
    }
}