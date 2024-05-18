package jp.co.yumemi.android.code_check.core.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingTextItem(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    isEnabled: Boolean = true,
) {
    val titleColor: Color
    val descriptionColor: Color

    if (isEnabled) {
        titleColor = MaterialTheme.colorScheme.onSurface
        descriptionColor = MaterialTheme.colorScheme.onSurfaceVariant
    } else {
        MaterialTheme.colorScheme.onSurface
            .copy(alpha = 0.38f)
            .compositeOver(MaterialTheme.colorScheme.surface)
            .also {
                titleColor = it
                descriptionColor = it
            }
    }

    Column(
        modifier = modifier
            .combinedClickable(
                enabled = isEnabled,
                onClick = { onClick.invoke() },
                onLongClick = { onLongClick.invoke() },
            )
            .padding(horizontal = 24.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.CenterVertically,
        ),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = titleColor,
        )

        if (description != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = descriptionColor,
            )
        }
    }
}

@Composable
fun SettingTextItem(
    title: Int,
    modifier: Modifier = Modifier,
    description: Int? = null,
    onClick: () -> Unit = {},
    onLongClick: () -> Unit = {},
    isEnabled: Boolean = true,
) {
    SettingTextItem(
        modifier = modifier,
        title = stringResource(title),
        description = description?.let { stringResource(it) },
        onClick = onClick,
        onLongClick = onLongClick,
        isEnabled = isEnabled,
    )
}
