package jp.co.yumemi.android.code_check.feature.home.trend

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName

const val HomeTrendRoute = "homeTrending"

fun NavController.navigateToHomeTrend(navOptions: NavOptions? = null) {
    this.navigate(HomeTrendRoute, navOptions)
}

fun NavGraphBuilder.homeTrendScreen(
    openDrawer: () -> Unit,
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
) {
    composable(HomeTrendRoute) {
        HomeTrendRoute(
            modifier = Modifier.fillMaxSize(),
            openDrawer = openDrawer,
            navigateToRepositoryDetail = navigateToRepositoryDetail,
        )
    }
}
