package jp.co.yumemi.android.code_check.feature.home.trend.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.core.common.extensions.drawVerticalScrollbar
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhTrendRepository
import jp.co.yumemi.android.code_check.core.ui.components.TrendRepositoryItem
import jp.co.yumemi.android.code_check.core.ui.extensions.plus
import jp.co.yumemi.android.code_check.core.ui.theme.LocalWindowWidthSize
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun HomeTrendIdleSection(
    trendRepositories: ImmutableList<GhTrendRepository>,
    favoriteRepoNames: ImmutableList<GhRepositoryName>,
    contentPadding: PaddingValues,
    onRequestOgImageLink: suspend (GhRepositoryName) -> String,
    onClickRepository: (GhRepositoryName) -> Unit,
    onClickAddFavorite: (GhRepositoryName) -> Unit,
    onClickRemoveFavorite: (GhRepositoryName) -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberLazyGridState()

    val columns = when (LocalWindowWidthSize.current) {
        WindowWidthSizeClass.Compact -> 1
        WindowWidthSizeClass.Medium -> 2
        WindowWidthSizeClass.Expanded -> 2
        else -> 1
    }

    LazyVerticalGrid(
        modifier = modifier.drawVerticalScrollbar(state, columns),
        state = state,
        columns = GridCells.Fixed(columns),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding + PaddingValues(16.dp),
    ) {
        items(
            items = trendRepositories,
            key = { it.repoName.toString() },
        ) {
            TrendRepositoryItem(
                modifier = Modifier.fillMaxWidth(),
                trendRepository = it,
                isFavorite = favoriteRepoNames.contains(it.repoName),
                onRequestOgImageLink = onRequestOgImageLink,
                onClickRepository = onClickRepository,
                onClickAddFavorite = onClickAddFavorite,
                onClickRemoveFavorite = onClickRemoveFavorite,
            )
        }
    }
}
