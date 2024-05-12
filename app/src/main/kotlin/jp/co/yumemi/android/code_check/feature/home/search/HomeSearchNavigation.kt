package jp.co.yumemi.android.code_check.feature.home.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

const val HomeSearchRoute = "homeSearch"

fun NavController.navigateToHomeSearch(navOptions: NavOptions? = null) {
    this.navigate(HomeSearchRoute, navOptions)
}

fun NavGraphBuilder.homeSearchScreen(
    openDrawer: () -> Unit,
) {
    composable(HomeSearchRoute) {
        HomeSearchRoute(
            modifier = Modifier.fillMaxSize(),
            openDrawer = openDrawer,
        )
    }
}
