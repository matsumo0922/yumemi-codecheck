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
import jp.co.yumemi.android.code_check.feature.home.HomeRoute
import jp.co.yumemi.android.code_check.feature.home.homeScreen
import jp.co.yumemi.android.code_check.feature.repo.navigateToRepositoryDetail
import jp.co.yumemi.android.code_check.feature.repo.repositoryDetailScreen

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
    ) {
        homeScreen(
            navigateToRepositoryDetail = navController::navigateToRepositoryDetail,
        )

        repositoryDetailScreen(
            navigateToWeb = { navigateToWeb(context, it) },
            terminate = navController::popBackStack,
        )
    }
}

private fun navigateToWeb(context: Context, url: String) {
    runCatching {
        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}
