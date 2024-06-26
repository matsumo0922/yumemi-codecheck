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
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.core.common.extensions.drawVerticalScrollbar
import jp.co.yumemi.android.code_check.core.model.GhLanguage
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.ui.components.RepositoryItem
import jp.co.yumemi.android.code_check.core.ui.extensions.plus
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun HomeFavoriteIdleSection(
    favoriteRepositories: ImmutableList<GhRepositoryDetail>,
    favoriteRepoNames: ImmutableList<GhRepositoryName>,
    languages: ImmutableList<GhLanguage>,
    onClickRepository: (GhRepositoryName) -> Unit,
    onClickAddFavorite: (GhRepositoryName) -> Unit,
    onClickRemoveFavorite: (GhRepositoryName) -> Unit,
    contentPadding: PaddingValues,
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
            items = favoriteRepositories,
            key = { it.repoName.toString() },
        ) { repositoryDetail ->
            RepositoryItem(
                modifier = Modifier
                    .animateItemPlacement()
                    .fillMaxWidth(),
                isFavorite = favoriteRepoNames.contains(repositoryDetail.repoName),
                item = repositoryDetail,
                languageColor = languages.find { it.title == repositoryDetail.language }?.color,
                onClickRepository = onClickRepository,
                onClickAddFavorite = onClickAddFavorite,
                onClickRemoveFavorite = onClickRemoveFavorite,
            )
        }
    }
}
