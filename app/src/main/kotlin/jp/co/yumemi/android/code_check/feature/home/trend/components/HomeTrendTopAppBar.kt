package jp.co.yumemi.android.code_check.feature.home.trend.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import me.matsumo.yumemi.codecheck.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeTrendTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior?,
    onClickDrawer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = stringResource(R.string.trending_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        },
        navigationIcon = {
            IconButton(onClick = onClickDrawer) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Drawer",
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}