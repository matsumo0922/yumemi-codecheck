package jp.co.yumemi.android.code_check.app.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import jp.co.yumemi.android.code_check.core.ui.animation.NavigateAnimation
import jp.co.yumemi.android.code_check.feature.home.HomeRoute
import jp.co.yumemi.android.code_check.feature.home.homeScreen
import jp.co.yumemi.android.code_check.feature.repo.navigateToRepositoryDetail
import jp.co.yumemi.android.code_check.feature.repo.repositoryDetailScreen
import jp.co.yumemi.android.code_check.feature.setting.oss.navigateToSettingOss
import jp.co.yumemi.android.code_check.feature.setting.oss.settingOssScreen
import jp.co.yumemi.android.code_check.feature.setting.theme.navigateToSettingTheme
import jp.co.yumemi.android.code_check.feature.setting.theme.settingThemeScreen
import jp.co.yumemi.android.code_check.feature.setting.top.navigateToSettingTop
import jp.co.yumemi.android.code_check.feature.setting.top.settingTopScreen

@Composable
fun YacNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val context = LocalContext.current

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeRoute,
        enterTransition = { NavigateAnimation.Horizontal.enter },
        exitTransition = { NavigateAnimation.Horizontal.exit },
        popEnterTransition = { NavigateAnimation.Horizontal.popEnter },
        popExitTransition = { NavigateAnimation.Horizontal.popExit },
    ) {
        homeScreen(
            navigateToRepositoryDetail = navController::navigateToRepositoryDetail,
            navigateToSettingTheme = navController::navigateToSettingTop,
        )

        repositoryDetailScreen(
            navigateToWeb = { navigateToWeb(context, it) },
            terminate = navController::popBackStack,
        )

        settingTopScreen(
            navigateToSettingTheme = navController::navigateToSettingTheme,
            navigateToSettingOss = navController::navigateToSettingOss,
            terminate = navController::popBackStack
        )

        settingThemeScreen(
            terminate = navController::popBackStack
        )

        settingOssScreen(
            terminate = navController::popBackStack
        )
    }
}

private fun navigateToWeb(context: Context, url: String) {
    runCatching {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
