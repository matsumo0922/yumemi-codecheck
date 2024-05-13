package jp.co.yumemi.android.code_check.feature.home.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName

const val HomeSearchRoute = "homeSearch"

fun NavController.navigateToHomeSearch(navOptions: NavOptions? = null) {
    this.navigate(HomeSearchRoute, navOptions)
}

fun NavGraphBuilder.homeSearchScreen(
    openDrawer: () -> Unit,
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
) {
    composable(HomeSearchRoute) {
        HomeSearchRoute(
            modifier = Modifier.fillMaxSize(),
            openDrawer = openDrawer,
            navigateToRepositoryDetail = navigateToRepositoryDetail,
        )
    }
}
