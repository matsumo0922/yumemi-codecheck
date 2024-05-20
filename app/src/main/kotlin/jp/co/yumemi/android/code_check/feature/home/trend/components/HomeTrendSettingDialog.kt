package jp.co.yumemi.android.code_check.feature.home.trend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import jp.co.yumemi.android.code_check.core.common.extensions.toColor
import jp.co.yumemi.android.code_check.core.model.GhLanguage
import jp.co.yumemi.android.code_check.core.model.GhTrendSince
import jp.co.yumemi.android.code_check.core.ui.extensions.ComponentPreviews
import jp.co.yumemi.android.code_check.core.ui.theme.YacTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import me.matsumo.yumemi.codecheck.R

@Composable
internal fun HomeTrendSettingDialog(
    selectedSince: GhTrendSince,
    selectedLanguage: GhLanguage?,
    allLanguages: ImmutableList<GhLanguage>,
    onClickUpdate: (GhTrendSince, GhLanguage?) -> Unit,
    onDismiss: () -> Unit,
) {
    val (since, setSince) = remember { mutableStateOf(selectedSince) }
    val (language, setLanguage) = remember { mutableStateOf(selectedLanguage) }
    val (searchQuery, onUpdateSearchQuery) = remember { mutableStateOf("") }
    val filteredLanguages = remember { mutableStateListOf<GhLanguage>() }

    LaunchedEffect(searchQuery) {
        filteredLanguages.clear()

        if (searchQuery.isNotBlank()) {
            filteredLanguages.addAll(
                allLanguages.filter { it.title.contains(searchQuery, ignoreCase = true) }.take(10),
            )
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            SinceSection(
                selectedSince = since,
                onClickSince = setSince,
            )

            LanguagesSection(
                searchQuery = searchQuery,
                languages = filteredLanguages.toImmutableList(),
                selectedLanguage = language,
                onUpdateSearchQuery = onUpdateSearchQuery,
                onClickLanguage = {
                    if (language == it) {
                        setLanguage.invoke(null)
                    } else {
                        setLanguage.invoke(it)
                    }
                },
            )

            Row(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                OutlinedButton(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    onClick = { onDismiss.invoke() },
                ) {
                    Text(text = stringResource(R.string.common_cancel))
                }

                Button(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    onClick = {
                        onClickUpdate.invoke(since, language)
                        onDismiss.invoke()
                    },
                ) {
                    Text(text = stringResource(R.string.common_ok))
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SinceSection(
    selectedSince: GhTrendSince,
    onClickSince: (GhTrendSince) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )

            Text(
                text = stringResource(R.string.trending_since),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        HorizontalDivider()

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            for (since in GhTrendSince.entries) {
                FilterChip(
                    selected = (selectedSince == since),
                    onClick = { onClickSince.invoke(since) },
                    label = {
                        Text(since.value)
                    },
                    leadingIcon = {
                        if (selectedSince == since) {
                            Icon(
                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                                imageVector = Icons.Default.Done,
                                contentDescription = null,
                            )
                        }
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun LanguagesSection(
    searchQuery: String,
    languages: ImmutableList<GhLanguage>,
    selectedLanguage: GhLanguage?,
    onUpdateSearchQuery: (String) -> Unit,
    onClickLanguage: (GhLanguage) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Default.Language,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
            )

            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.trending_language),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }

        HorizontalDivider()

        TextField(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            value = searchQuery,
            onValueChange = onUpdateSearchQuery,
            placeholder = { Text(stringResource(R.string.common_search)) },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onUpdateSearchQuery.invoke("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                        )
                    }
                }
            },
        )

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            for (language in languages) {
                FilterChip(
                    selected = (selectedLanguage == language),
                    onClick = { onClickLanguage.invoke(language) },
                    label = {
                        Text(language.title)
                    },
                    leadingIcon = {
                        if (selectedLanguage == language) {
                            Icon(
                                modifier = Modifier.size(FilterChipDefaults.IconSize),
                                imageVector = Icons.Default.Done,
                                contentDescription = null,
                            )
                        }
                    },
                )
            }
        }
    }
}

@ComponentPreviews
@Composable
private fun HomeTrendSettingDialogPreview() {
    val languages = persistentListOf(
        GhLanguage("#A97BFF".toColor(), "kotlin", "Kotlin", ""),
        GhLanguage("#FFA07A".toColor(), "java", "Java", ""),
        GhLanguage("#F05138".toColor(), "swift", "Swift", ""),
        GhLanguage("#FFD700".toColor(), "javascript", "JavaScript", ""),
        GhLanguage("#FF69B4".toColor(), "ruby", "Ruby", ""),
        GhLanguage("#FF6347".toColor(), "python", "Python", ""),
        GhLanguage("#FF4500".toColor(), "c++", "C++", ""),
        GhLanguage("#FF1493".toColor(), "c#", "C#", ""),
        GhLanguage("#FF0000".toColor(), "c", "C", ""),
        GhLanguage("#D3D3D3".toColor(), "go", "Go", ""),
        GhLanguage("#808080".toColor(), "shell", "Shell", ""),
        GhLanguage("#000000".toColor(), "other", "Other", ""),
    )

    YacTheme {
        HomeTrendSettingDialog(
            selectedSince = GhTrendSince.DAILY,
            selectedLanguage = languages.first(),
            allLanguages = languages,
            onClickUpdate = { _, _ -> },
            onDismiss = {},
        )
    }
}
