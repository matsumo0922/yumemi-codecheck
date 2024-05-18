package jp.co.yumemi.android.code_check.feature.home.trend.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.core.extensions.drawVerticalScrollbar
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhTrendRepository
import jp.co.yumemi.android.code_check.core.ui.components.TrendRepositoryItem
import jp.co.yumemi.android.code_check.core.ui.extensions.plus
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
    val state = rememberLazyListState()

    LazyColumn(
        modifier = modifier.drawVerticalScrollbar(state),
        state = state,
        verticalArrangement = Arrangement.spacedBy(16.dp),
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
