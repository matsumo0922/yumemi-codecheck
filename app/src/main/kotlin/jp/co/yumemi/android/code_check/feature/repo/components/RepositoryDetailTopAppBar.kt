package jp.co.yumemi.android.code_check.feature.repo.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import jp.co.yumemi.android.code_check.core.ui.extensions.ComponentPreviews
import jp.co.yumemi.android.code_check.core.ui.theme.YacTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RepositoryDetailTopAppBar(
    isFavorite: Boolean,
    scrollBehavior: TopAppBarScrollBehavior?,
    onClickWeb: () -> Unit,
    onClickAddFavorite: () -> Unit,
    onClickRemoveFavorite: () -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            // no title
        },
        navigationIcon = {
            IconButton(onClick = onClickBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                )
            }
        },
        actions = {
            IconButton(onClick = onClickWeb) {
                Icon(
                    imageVector = Icons.Default.OpenInBrowser,
                    contentDescription = "Open in browser",
                )
            }

            IconButton(onClick = if (isFavorite) onClickRemoveFavorite else onClickAddFavorite) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    tint = if (isFavorite) Color.Red else LocalContentColor.current,
                    contentDescription = "toggle favorite",
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@ComponentPreviews
@Composable
fun RepositoryDetailTopAppBarPreview() {
    YacTheme {
        RepositoryDetailTopAppBar(
            isFavorite = false,
            scrollBehavior = null,
            onClickWeb = {},
            onClickAddFavorite = {},
            onClickRemoveFavorite = {},
            onClickBack = {},
        )
    }
}
