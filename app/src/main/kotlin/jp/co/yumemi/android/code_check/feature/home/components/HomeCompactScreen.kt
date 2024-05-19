package jp.co.yumemi.android.code_check.feature.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.feature.home.HomeNavHost
import jp.co.yumemi.android.code_check.feature.home.navigateToHomeDestination
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun HomeCompactScreen(
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
    navigateToSettingTop: () -> Unit,
    navigateToAboutApp: () -> Unit,
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
                buildConfig = koinInject(),
                currentDestination = navController.currentBackStackEntryAsState().value?.destination,
                navigateToLibraryScreen = navController::navigateToHomeDestination,
                navigateToSetting = navigateToSettingTop,
                navigateToAbout = navigateToAboutApp,
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
