package jp.co.yumemi.android.code_check.feature.home.search.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.core.model.GhSearchHistory
import jp.co.yumemi.android.code_check.core.ui.extensions.ComponentPreviews
import jp.co.yumemi.android.code_check.core.ui.theme.YacTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.Instant
import me.matsumo.yumemi.codecheck.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class, ExperimentalAnimationGraphicsApi::class)
@Composable
internal fun HomeSearchTopAppBar(
    query: String,
    suggestions: ImmutableList<GhSearchHistory>,
    searchHistories: ImmutableList<GhSearchHistory>,
    onClickDrawerMenu: () -> Unit,
    onClickSearch: (String) -> Unit,
    onClickSort: () -> Unit,
    onClickRemoveSearchHistory: (GhSearchHistory) -> Unit,
    onUpdateQuery: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val image = AnimatedImageVector.animatedVectorResource(R.drawable.av_drawer_to_arrow)

    val (isEnd, setEnd) = remember { mutableStateOf(false) }
    val (isActive, setActive) = remember { mutableStateOf(false) }

    LaunchedEffect(isActive) {
        setEnd.invoke(isActive)
    }

    SearchBar(
        modifier = modifier,
        query = query,
        onQueryChange = onUpdateQuery,
        onSearch = {
            if (it.isNotBlank()) {
                setActive.invoke(false)
                onClickSearch.invoke(it)
            }
        },
        active = isActive,
        onActiveChange = setActive,
        colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
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
                painter = rememberAnimatedVectorPainter(image, isEnd),
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
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(
                items = searchHistories,
                key = { it.query },
            ) {
                AnimatedVisibility(
                    modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null),
                    visible = suggestions.contains(it),
                    enter = fadeIn(tween(150)),
                    exit = fadeOut(tween(150)),
                ) {
                    HomeSearchHistoryItem(
                        modifier = Modifier.fillMaxWidth(),
                        searchHistory = it,
                        onClick = {
                            setActive.invoke(false)
                            onUpdateQuery.invoke(it.query)
                            onClickSearch.invoke(it.query)
                        },
                        onClickRemove = {
                            onClickRemoveSearchHistory.invoke(it)
                        },
                    )
                }
            }

            item("Button") {
                OutlinedButton(
                    modifier = Modifier.padding(16.dp),
                    onClick = onClickSort,
                ) {
                    Text("${stringResource(R.string.search_order)} / ${stringResource(R.string.search_sort)}")
                }
            }
        }
    }
}

@ComponentPreviews
@Composable
private fun HomeSearchTopAppBarPreview() {
    val searchHistories = persistentListOf(
        GhSearchHistory("query1", Instant.DISTANT_FUTURE),
        GhSearchHistory("query2", Instant.DISTANT_FUTURE),
        GhSearchHistory("query3", Instant.DISTANT_FUTURE),
    )

    YacTheme {
        HomeSearchTopAppBar(
            modifier = Modifier.fillMaxWidth(),
            query = "query",
            suggestions = searchHistories,
            searchHistories = searchHistories,
            onClickDrawerMenu = {},
            onClickSearch = {},
            onClickRemoveSearchHistory = {},
            onClickSort = {},
            onUpdateQuery = {},
        )
    }
}
