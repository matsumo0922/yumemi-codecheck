package jp.co.yumemi.android.code_check.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import jp.co.yumemi.android.code_check.core.model.YacBuildConfig
import kotlinx.coroutines.launch
import me.matsumo.yumemi.codecheck.R

@Composable
fun HomeDrawerContent(
    state: DrawerState?,
    buildConfig: YacBuildConfig,
    currentDestination: NavDestination?,
    navigateToLibraryScreen: (HomeDestination) -> Unit,
    navigateToSetting: () -> Unit,
    navigateToAbout: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .width(256.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
    ) {
        HeaderItem(
            modifier = Modifier
                .statusBarsPadding()
                .fillMaxWidth(),
            buildConfig = buildConfig,
        )

        NavigationDrawerItem(
            state = state,
            isSelected = currentDestination.isHomeDestinationInHierarchy(HomeDestination.SEARCH),
            label = stringResource(R.string.navigation_search),
            icon = Icons.Outlined.Home,
            selectedIcon = Icons.Default.Search,
            onClick = { navigateToLibraryScreen.invoke(HomeDestination.SEARCH) },
        )

        NavigationDrawerItem(
            state = state,
            isSelected = currentDestination.isHomeDestinationInHierarchy(HomeDestination.TRENDING),
            label = stringResource(R.string.navigation_trending),
            icon = Icons.Outlined.Search,
            selectedIcon = Icons.AutoMirrored.Filled.TrendingUp,
            onClick = { navigateToLibraryScreen.invoke(HomeDestination.TRENDING) },
        )

        NavigationDrawerItem(
            state = state,
            isSelected = currentDestination.isHomeDestinationInHierarchy(HomeDestination.FAVORITE),
            label = stringResource(R.string.navigation_favorite),
            icon = Icons.Outlined.Search,
            selectedIcon = Icons.Default.Favorite,
            onClick = { navigateToLibraryScreen.invoke(HomeDestination.FAVORITE) },
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
        )

        NavigationDrawerItem(
            state = state,
            label = stringResource(R.string.navigation_settings),
            icon = Icons.Default.Settings,
            onClick = navigateToSetting,
        )

        NavigationDrawerItem(
            state = state,
            label = stringResource(R.string.navigation_about),
            icon = Icons.Outlined.Info,
            onClick = navigateToAbout,
        )
    }
}

@Composable
private fun HeaderItem(
    buildConfig: YacBuildConfig,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(24.dp, 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Yumemi Android Engineer Code Check",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        Text(
            text = "Version ${buildConfig.versionName}:${buildConfig.versionCode}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun NavigationDrawerItem(
    state: DrawerState?,
    label: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    selectedIcon: ImageVector = icon,
) {
    val scope = rememberCoroutineScope()
    val containerColor: Color
    val contentColor: Color

    if (isSelected) {
        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
        contentColor = MaterialTheme.colorScheme.primary
    } else {
        containerColor = Color.Transparent
        contentColor = MaterialTheme.colorScheme.onSurface
    }

    Row(
        modifier = modifier
            .padding(end = 16.dp)
            .clip(
                RoundedCornerShape(
                    topEnd = 32.dp,
                    bottomEnd = 32.dp,
                ),
            )
            .background(containerColor)
            .clickable {
                scope.launch {
                    state?.close()
                    onClick.invoke()
                }
            }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = if (isSelected) selectedIcon else icon,
            contentDescription = null,
            tint = contentColor,
        )

        Text(
            modifier = Modifier.weight(1f),
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = contentColor,
        )
    }
}
