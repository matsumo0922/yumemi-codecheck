package jp.co.yumemi.android.code_check.feature.repo.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Merge
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.ui.extensions.ComponentPreviews
import jp.co.yumemi.android.code_check.core.ui.previews.GhRepositoryDetailPreviewParameter
import jp.co.yumemi.android.code_check.core.ui.theme.YacTheme

@Composable
internal fun RepositoryDetailActionsSection(
    repositoryDetail: GhRepositoryDetail,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HorizontalDivider()

        ActionsButton(
            modifier = Modifier.fillMaxWidth(),
            title = "Issues",
            icon = Icons.Default.BugReport,
            color = Color.Green,
            count = repositoryDetail.openIssuesCount,
            onClick = { /*TODO*/ },
        )

        ActionsButton(
            modifier = Modifier.fillMaxWidth(),
            title = "Pull Requests",
            icon = Icons.Default.Merge,
            color = Color.Blue,
            count = null,
            onClick = { /*TODO*/ },
        )

        ActionsButton(
            modifier = Modifier.fillMaxWidth(),
            title = "Releases",
            icon = Icons.Default.Tag,
            color = Color.Gray,
            count = null,
            onClick = { /*TODO*/ },
        )

        HorizontalDivider()
    }
}

@Composable
private fun ActionsButton(
    title: String,
    icon: ImageVector,
    color: Color,
    count: Int?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onClick.invoke() }
            .padding(16.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Card(
            modifier = Modifier.size(24.dp),
            shape = RoundedCornerShape(4.dp),
            colors = CardDefaults.cardColors(color.copy(alpha = 0.5f)),
        ) {
            Icon(
                modifier = Modifier.padding(4.dp),
                imageVector = icon,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = null,
            )
        }

        Text(
            modifier = Modifier.weight(1f),
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )

        if (count != null) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@ComponentPreviews
@Composable
private fun RepositoryDetailActionsSectionPreview(
    @PreviewParameter(GhRepositoryDetailPreviewParameter::class) repositoryDetail: GhRepositoryDetail,
) {
    YacTheme {
        RepositoryDetailActionsSection(
            modifier = Modifier.fillMaxWidth(),
            repositoryDetail = repositoryDetail,
        )
    }
}
