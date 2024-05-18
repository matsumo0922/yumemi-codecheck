package jp.co.yumemi.android.code_check.feature.setting.oss

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SettingOssRoute = "settingOss"

fun NavController.navigateToSettingOss() {
    this.navigate(SettingOssRoute)
}

fun NavGraphBuilder.settingOssScreen(
    terminate: () -> Unit,
) {
    composable(SettingOssRoute) {
        SettingOssScreen(
            modifier = Modifier.fillMaxSize(),
            terminate = terminate,
        )
    }
}
