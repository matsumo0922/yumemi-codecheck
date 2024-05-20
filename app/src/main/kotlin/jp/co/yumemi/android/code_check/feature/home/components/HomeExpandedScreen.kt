package jp.co.yumemi.android.code_check.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.feature.home.HomeNavHost
import jp.co.yumemi.android.code_check.feature.home.navigateToHomeDestination
import org.koin.compose.koinInject

@Composable
fun HomeExpandedScreen(
    drawerState: DrawerState,
    navController: NavHostController,
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
    navigateToSettingTop: () -> Unit,
    navigateToAboutApp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PermanentNavigationDrawer(
        modifier = modifier,
        drawerContent = {
            HomeDrawerContent(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxHeight(),
                state = drawerState,
                buildConfig = koinInject(),
                currentDestination = navController.currentBackStackEntryAsState().value?.destination,
                navigateToLibraryScreen = navController::navigateToHomeDestination,
                navigateToSetting = navigateToSettingTop,
                navigateToAbout = navigateToAboutApp,
            )
        },
    ) {
        HomeNavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            openDrawer = {
                // do nothing
            },
            navigateToRepositoryDetail = navigateToRepositoryDetail,
        )
    }
}
