package jp.co.yumemi.android.code_check.feature.home.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import me.matsumo.yumemi.codecheck.R

enum class HomeDestination(
    val selectedIcon: ImageVector,
    val deselectedIcon: ImageVector,
    val title: Int,
) {
    SEARCH(
        selectedIcon = Icons.Default.Search,
        deselectedIcon = Icons.Outlined.Search,
        title = R.string.navigation_search,
    ),
    FAVORITE(
        selectedIcon = Icons.Default.Favorite,
        deselectedIcon = Icons.Outlined.Favorite,
        title = R.string.navigation_favorite,
    ),
}

internal fun NavDestination?.isHomeDestinationInHierarchy(destination: HomeDestination): Boolean {
    return this?.hierarchy?.any { it.route?.contains(destination.name, true) ?: false } == true
}
