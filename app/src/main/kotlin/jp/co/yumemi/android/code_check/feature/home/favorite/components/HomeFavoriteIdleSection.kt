package jp.co.yumemi.android.code_check.feature.home.favorite.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.core.extensions.drawVerticalScrollbar
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.ui.component.RepositoryItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.ImmutableMap

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeFavoriteIdleSection(
    favoriteRepositories: ImmutableList<GhRepositoryDetail>,
    favoriteRepoNames: ImmutableList<GhRepositoryName>,
    languageColors: ImmutableMap<String, Color?>,
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
        contentPadding = PaddingValues(16.dp),
    ) {
        items(
            items = favoriteRepositories,
            key = { it.id },
        ) {
            RepositoryItem(
                modifier = Modifier
                    .animateItemPlacement()
                    .fillMaxWidth(),
                isFavorite = favoriteRepoNames.contains(it.repoName),
                item = it,
                languageColor = languageColors[it.language],
                onClickRepository = onClickRepository,
                onClickAddFavorite = onClickAddFavorite,
                onClickRemoveFavorite = onClickRemoveFavorite,
            )
        }
    }
}
