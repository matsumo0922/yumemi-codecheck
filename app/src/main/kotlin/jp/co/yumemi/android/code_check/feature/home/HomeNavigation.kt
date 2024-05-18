package jp.co.yumemi.android.code_check.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName

const val HomeRoute = "home"

fun NavController.navigateToHome() {
    this.navigate(HomeRoute)
}

fun NavGraphBuilder.homeScreen(
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
    navigateToSettingTop: () -> Unit,
    navigateToAboutApp: () -> Unit,
) {
    composable(HomeRoute) {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            navigateToRepositoryDetail = navigateToRepositoryDetail,
            navigateToSettingTop = navigateToSettingTop,
            navigateToAboutApp = navigateToAboutApp,
        )
    }
}
