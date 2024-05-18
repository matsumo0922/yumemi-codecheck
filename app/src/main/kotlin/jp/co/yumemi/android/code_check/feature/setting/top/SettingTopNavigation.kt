package jp.co.yumemi.android.code_check.feature.setting.top

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SettingTopRoute = "settingTop"

fun NavController.navigateToSettingTop() {
    this.navigate(SettingTopRoute)
}

fun NavGraphBuilder.settingTopScreen(
    navigateToSettingTheme: () -> Unit,
    navigateToSettingOss: () -> Unit,
    terminate: () -> Unit,
) {
    composable(SettingTopRoute) {
        SettingTopRoute(
            modifier = Modifier.fillMaxSize(),
            navigateToSettingTheme = navigateToSettingTheme,
            navigateToSettingOss = navigateToSettingOss,
            terminate = terminate,
        )
    }
}
