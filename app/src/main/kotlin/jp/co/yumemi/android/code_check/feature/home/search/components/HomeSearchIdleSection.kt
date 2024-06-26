package jp.co.yumemi.android.code_check.feature.home.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import jp.co.yumemi.android.code_check.core.common.extensions.drawVerticalScrollbar
import jp.co.yumemi.android.code_check.core.model.GhLanguage
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhSearchRepositories
import jp.co.yumemi.android.code_check.core.model.asRepositoryDetail
import jp.co.yumemi.android.code_check.core.ui.components.RepositoryItem
import jp.co.yumemi.android.code_check.core.ui.extensions.IntRangeSaver
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun HomeSearchIdleSection(
    query: String,
    favoriteRepoNames: ImmutableList<GhRepositoryName>,
    languages: ImmutableList<GhLanguage>,
    pagingAdapter: LazyPagingItems<GhSearchRepositories.Item>,
    contentPadding: PaddingValues,
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
        contentPadding = contentPadding,
    ) {
        items(
            count = pagingAdapter.itemCount,
            key = pagingAdapter.itemKey { it.id },
            contentType = pagingAdapter.itemContentType(),
        ) { index ->
            pagingAdapter[index]?.let { item ->
                val markupRange = rememberSaveable(saver = IntRangeSaver) { getMatchRange(query, item.repoName.toString()) }

                RepositoryItem(
                    modifier = Modifier.fillMaxWidth(),
                    isFavorite = favoriteRepoNames.contains(item.repoName),
                    item = item.asRepositoryDetail(),
                    markupRange = markupRange,
                    languageColor = languages.find { it.title == item.language }?.color,
                    onClickRepository = onClickRepository,
                    onClickAddFavorite = onClickAddFavorite,
                    onClickRemoveFavorite = onClickRemoveFavorite,
                )
            }
        }
    }
}

private fun getMatchRange(query: String, text: String): IntRange {
    val regex = Regex("(?i)$query", RegexOption.IGNORE_CASE)
    return regex.find(text)?.range ?: 0..0
}
