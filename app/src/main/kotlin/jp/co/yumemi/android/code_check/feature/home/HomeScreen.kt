package jp.co.yumemi.android.code_check.feature.home

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.ui.theme.LocalWindowWidthSize
import jp.co.yumemi.android.code_check.feature.home.components.HomeCompactScreen
import jp.co.yumemi.android.code_check.feature.home.components.HomeDestination
import jp.co.yumemi.android.code_check.feature.home.components.HomeExpandedScreen
import jp.co.yumemi.android.code_check.feature.home.components.HomeMediumScreen
import jp.co.yumemi.android.code_check.feature.home.favorite.navigateToHomeFavorite
import jp.co.yumemi.android.code_check.feature.home.search.navigateToHomeSearch
import jp.co.yumemi.android.code_check.feature.home.trend.navigateToHomeTrend

@Composable
fun HomeScreen(
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
    navigateToSettingTop: () -> Unit,
    navigateToAboutApp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (LocalWindowWidthSize.current) {
        WindowWidthSizeClass.Compact -> {
            HomeCompactScreen(
                modifier = modifier,
                navigateToRepositoryDetail = navigateToRepositoryDetail,
                navigateToSettingTop = navigateToSettingTop,
                navigateToAboutApp = navigateToAboutApp,
            )
        }

        WindowWidthSizeClass.Medium -> {
            HomeMediumScreen(
                modifier = modifier,
                navigateToRepositoryDetail = navigateToRepositoryDetail,
                navigateToSettingTop = navigateToSettingTop,
                navigateToAboutApp = navigateToAboutApp,
            )
        }

        WindowWidthSizeClass.Expanded -> {
            HomeExpandedScreen(
                modifier = modifier,
                navigateToRepositoryDetail = navigateToRepositoryDetail,
                navigateToSettingTop = navigateToSettingTop,
                navigateToAboutApp = navigateToAboutApp,
            )
        }
    }
}

fun NavHostController.navigateToHomeDestination(destination: HomeDestination) {
    val navOption = navOptions {
        popUpTo(graph.findStartDestination().route.orEmpty()) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

    when (destination) {
        HomeDestination.SEARCH -> navigateToHomeSearch(navOption)
        HomeDestination.TRENDING -> navigateToHomeTrend(navOption)
        HomeDestination.FAVORITE -> navigateToHomeFavorite(navOption)
    }
}
