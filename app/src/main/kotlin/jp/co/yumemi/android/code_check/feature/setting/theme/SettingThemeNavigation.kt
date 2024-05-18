package jp.co.yumemi.android.code_check.feature.setting.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SettingThemeRoute = "settingTheme"

fun NavController.navigateToSettingTheme() {
    this.navigate(SettingThemeRoute)
}

fun NavGraphBuilder.settingThemeScreen(
    terminate: () -> Unit,
) {
    composable(SettingThemeRoute) {
        SettingThemeRoute(
            modifier = Modifier.fillMaxSize(),
            terminate = terminate,
        )
    }
}
