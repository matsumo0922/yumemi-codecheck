package jp.co.yumemi.android.code_check.feature.home.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import jp.co.yumemi.android.code_check.core.ui.component.YacNavigationBar
import jp.co.yumemi.android.code_check.core.ui.component.YacNavigationBarItem
import jp.co.yumemi.android.code_check.core.ui.component.YacNavigationDefaults
import kotlinx.collections.immutable.ImmutableList

@Composable
fun HomeBottomBar(
    destinations: ImmutableList<HomeDestination>,
    navigateToDestination: (HomeDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    YacNavigationBar(modifier) {
        destinations.forEach { destination ->
            val isSelected = currentDestination.isHomeDestinationInHierarchy(destination)

            YacNavigationBarItem(
                isSelected = isSelected,
                onClick = { navigateToDestination(destination) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) destination.selectedIcon else destination.deselectedIcon,
                        contentDescription = stringResource(destination.title),
                        tint = if (isSelected) {
                            YacNavigationDefaults.navigationSelectedItemColor()
                        } else {
                            YacNavigationDefaults.navigationContentColor()
                        },
                    )
                },
                label = {
                    Text(
                        text = stringResource(destination.title),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                    )
                },
            )
        }
    }
}
