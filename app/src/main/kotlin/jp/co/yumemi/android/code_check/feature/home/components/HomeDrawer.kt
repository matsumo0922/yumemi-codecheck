package jp.co.yumemi.android.code_check.feature.home.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import jp.co.yumemi.android.code_check.core.model.YacBuildConfig

@Composable
fun HomeDrawer(
    state: DrawerState,
    buildConfig: YacBuildConfig,
    currentDestination: NavDestination?,
    navigateToLibraryScreen: (HomeDestination) -> Unit,
    navigateToSetting: () -> Unit,
    navigateToAbout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalDrawerSheet(
        drawerContainerColor = MaterialTheme.colorScheme.surface,
        windowInsets = WindowInsets(0, 0, 0, 0),
    ) {
        HomeDrawerContent(
            modifier = modifier,
            state = state,
            buildConfig = buildConfig,
            currentDestination = currentDestination,
            navigateToLibraryScreen = navigateToLibraryScreen,
            navigateToSetting = navigateToSetting,
            navigateToAbout = navigateToAbout,
        )
    }
}
