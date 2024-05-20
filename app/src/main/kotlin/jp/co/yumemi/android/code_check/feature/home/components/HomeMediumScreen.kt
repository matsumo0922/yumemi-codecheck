package jp.co.yumemi.android.code_check.feature.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.feature.home.HomeNavHost
import jp.co.yumemi.android.code_check.feature.home.navigateToHomeDestination
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun HomeMediumScreen(
    drawerState: DrawerState,
    navController: NavHostController,
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
    navigateToSettingTop: () -> Unit,
    navigateToAboutApp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        drawerContent = {
            HomeDrawer(
                modifier = Modifier.fillMaxHeight(),
                state = drawerState,
                buildConfig = koinInject(),
                currentDestination = navController.currentBackStackEntryAsState().value?.destination,
                navigateToLibraryScreen = navController::navigateToHomeDestination,
                navigateToSetting = navigateToSettingTop,
                navigateToAbout = navigateToAboutApp,
            )
        },
    ) {
        Row {
            HomeNavigationRail(
                modifier = Modifier.fillMaxHeight(),
                destinations = HomeDestination.entries.toImmutableList(),
                currentDestination = navController.currentBackStackEntryAsState().value?.destination,
                navigateToDestination = navController::navigateToHomeDestination,
            )

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
        }
    }
}
