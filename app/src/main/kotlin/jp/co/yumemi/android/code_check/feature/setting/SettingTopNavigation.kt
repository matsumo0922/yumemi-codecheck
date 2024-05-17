package jp.co.yumemi.android.code_check.feature.setting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.feature.home.HomeScreen

const val SettingTopRoute = "settingTop"

fun NavController.navigateToSettingTop() {
    this.navigate(SettingTopRoute)
}

fun NavGraphBuilder.settingTopScreen(
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
) {
    composable(SettingTopRoute) {
        HomeScreen(
            modifier = Modifier.fillMaxSize(),
            navigateToRepositoryDetail = navigateToRepositoryDetail,
        )
    }
}
