package jp.co.yumemi.android.code_check.feature.home.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import jp.co.yumemi.android.code_check.core.model.SearchRepositories
import jp.co.yumemi.android.code_check.core.ui.component.SearchRepositoryItem
import jp.co.yumemi.android.code_check.core.ui.extensions.IntRangeSaver

@Composable
internal fun HomeSearchIdleSection(
    query: String,
    pagingAdapter: LazyPagingItems<SearchRepositories.Item>,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = contentPadding
    ) {
        items(
            count = pagingAdapter.itemCount,
            key = pagingAdapter.itemKey { it.id },
            contentType = pagingAdapter.itemContentType(),
        ) { index ->
            pagingAdapter[index]?.let { item ->
                val markupRange = rememberSaveable(saver = IntRangeSaver) { getMatchRange(query, item.repoName.toString()) }

                SearchRepositoryItem(
                    modifier = Modifier.fillMaxWidth(),
                    isFavorite = false,
                    item = item,
                    markupRange = markupRange,
                    onClickRepository = {},
                    onClickAddFavorite = {},
                    onClickRemoveFavorite = {},
                )
            }
        }
    }
}

private fun getMatchRange(query: String, text: String): IntRange {
    val regex = Regex("(?i)$query", RegexOption.IGNORE_CASE)
    return regex.find(text)?.range ?: 0..0
}
