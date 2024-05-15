package jp.co.yumemi.android.code_check.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.feature.home.components.HomeBottomBar
import jp.co.yumemi.android.code_check.feature.home.components.HomeDestination
import jp.co.yumemi.android.code_check.feature.home.components.HomeDrawer
import jp.co.yumemi.android.code_check.feature.home.favorite.navigateToHomeFavorite
import jp.co.yumemi.android.code_check.feature.home.search.navigateToHomeSearch
import jp.co.yumemi.android.code_check.feature.home.trend.navigateToHomeTrend
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navController = rememberNavController()

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            HomeDrawer(
                modifier = Modifier.fillMaxHeight(),
                state = drawerState,
                currentDestination = navController.currentBackStackEntryAsState().value?.destination,
                navigateToLibraryScreen = navController::navigateToHomeDestination,
                navigateToSetting = {},
                navigateToAbout = {},
            )
        },
    ) {
        Column {
            HomeNavHost(
                modifier = Modifier.weight(1f),
                navController = navController,
                openDrawer = {
                    scope.launch {
                        drawerState.open()
                    }
                },
                navigateToRepositoryDetail = navigateToRepositoryDetail,
            )

            HomeBottomBar(
                modifier = Modifier.fillMaxWidth(),
                destinations = HomeDestination.entries.toImmutableList(),
                currentDestination = navController.currentBackStackEntryAsState().value?.destination,
                navigateToDestination = navController::navigateToHomeDestination,
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
