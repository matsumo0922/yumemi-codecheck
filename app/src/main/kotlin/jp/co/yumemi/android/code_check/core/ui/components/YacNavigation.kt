@file:Suppress("MatchingDeclarationName")

package jp.co.yumemi.android.code_check.core.ui.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun YacNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = YacNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content,
    )
}

@Composable
fun RowScope.YacNavigationBarItem(
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    NavigationBarItem(
        modifier = modifier,
        selected = isSelected,
        onClick = onClick,
        icon = icon,
        label = label,
        alwaysShowLabel = false,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = YacNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = YacNavigationDefaults.navigationContentColor(),
            selectedTextColor = YacNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = YacNavigationDefaults.navigationContentColor(),
            indicatorColor = YacNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

@Composable
fun YacNavigationRail(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    NavigationRail(
        modifier = modifier,
        contentColor = YacNavigationDefaults.navigationContentColor(),
        content = content,
    )
}

@Composable
fun ColumnScope.YacNavigationRailItem(
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    onClick: () -> Unit,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
) {
    NavigationRailItem(
        modifier = modifier,
        selected = isSelected,
        onClick = onClick,
        icon = icon,
        label = label,
        alwaysShowLabel = false,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = YacNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = YacNavigationDefaults.navigationContentColor(),
            selectedTextColor = YacNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = YacNavigationDefaults.navigationContentColor(),
            indicatorColor = YacNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

object YacNavigationDefaults {
    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}
