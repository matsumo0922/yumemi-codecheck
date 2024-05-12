package jp.co.yumemi.android.code_check.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.core.ui.extensions.ComponentPreviews
import jp.co.yumemi.android.code_check.core.ui.theme.YacTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TopicItems(
    topics: ImmutableList<String>,
    onClickTag: (String) -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    arrangementSpace: Dp = 8.dp,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(arrangementSpace),
        verticalArrangement = Arrangement.spacedBy(arrangementSpace),
    ) {
        for (tag in topics) {
            TopicItem(
                topic = tag,
                textStyle = textStyle,
                onClickTopic = onClickTag,
            )
        }
    }
}

@Composable
private fun TopicItem(
    topic: String,
    textStyle: TextStyle,
    onClickTopic: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClickTopic(topic) }
            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)),
    ) {
        Text(
            modifier = Modifier.padding(12.dp, 4.dp),
            text = topic,
            style = textStyle,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@ComponentPreviews
@Composable
private fun TopicItemPreview() {
    val topics = persistentListOf("Kotlin", "Java", "Swift", "Objective-C")

    YacTheme {
        TopicItems(
            topics = topics,
            onClickTag = {},
        )
    }
}
