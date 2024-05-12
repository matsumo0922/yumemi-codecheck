package jp.co.yumemi.android.code_check.feature.home.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.core.model.GhSearchHistory
import jp.co.yumemi.android.code_check.core.ui.extensions.ComponentPreviews
import kotlinx.datetime.Instant
import me.matsumo.yumemi.codecheck.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun HomeSearchTopAppBar(
    query: String,
    suggestions: List<GhSearchHistory>,
    searchHistories: List<GhSearchHistory>,
    onClickDrawerMenu: () -> Unit,
    onClickSearch: (String) -> Unit,
    onClickRemoveSearchHistory: (GhSearchHistory) -> Unit,
    onUpdateQuery: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val (isActive, setActive) = remember { mutableStateOf(false) }

    DockedSearchBar(
        modifier = modifier,
        query = query,
        onQueryChange = onUpdateQuery,
        onSearch = onClickSearch,
        active = isActive,
        onActiveChange = setActive,
        placeholder = { Text(stringResource(R.string.search_title)) },
        leadingIcon = {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(50))
                    .padding(6.dp)
                    .clickable {
                        if (isActive) {
                            setActive.invoke(false)
                        } else {
                            onClickDrawerMenu.invoke()
                        }
                    },
                imageVector = if (isActive) Icons.AutoMirrored.Filled.ArrowBack else Icons.Default.Menu,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(50))
                        .padding(6.dp)
                        .clickable {
                            onUpdateQuery.invoke("")
                        },
                    imageVector = Icons.Default.Clear,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(
                items = searchHistories,
                key = { it.query },
            ) {
                AnimatedVisibility(
                    modifier = Modifier.animateItemPlacement(),
                    visible = suggestions.contains(it),
                    enter = fadeIn(tween(150)),
                    exit = fadeOut(tween(150)),
                ) {
                    SearchHistoryItem(
                        modifier = Modifier.fillMaxWidth(),
                        searchHistory = it,
                        onClick = { onClickSearch.invoke(it.query) },
                        onClickRemove = { onClickRemoveSearchHistory.invoke(it) },
                    )
                }
            }
        }
    }
}

@Composable
private fun SearchHistoryItem(
    searchHistory: GhSearchHistory,
    onClick: () -> Unit,
    onClickRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable { onClick.invoke() }
            .padding(16.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = Icons.Default.History,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            modifier = Modifier.weight(1f),
            text = searchHistory.query,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        IconButton(
            modifier = Modifier.size(32.dp),
            onClick = { onClickRemove.invoke() },
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.Clear,
                contentDescription = "Remove search history \"${searchHistory.query}\"",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@ComponentPreviews
@Composable
private fun HomeSearchTopAppBarPreview() {
    val searchHistories = listOf(
        GhSearchHistory("query1", Instant.DISTANT_FUTURE),
        GhSearchHistory("query2", Instant.DISTANT_FUTURE),
        GhSearchHistory("query3", Instant.DISTANT_FUTURE),
    )

    HomeSearchTopAppBar(
        modifier = Modifier.fillMaxWidth(),
        query = "query",
        suggestions = searchHistories,
        searchHistories = searchHistories,
        onClickDrawerMenu = {},
        onClickSearch = {},
        onClickRemoveSearchHistory = {},
        onUpdateQuery = {},
    )
}
