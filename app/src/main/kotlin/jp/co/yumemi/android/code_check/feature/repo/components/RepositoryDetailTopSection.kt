package jp.co.yumemi.android.code_check.feature.repo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material.icons.outlined.ForkLeft
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.RemoveRedEye
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.ui.extensions.ComponentPreviews
import jp.co.yumemi.android.code_check.core.ui.previews.GhRepositoryDetailPreviewParameter
import jp.co.yumemi.android.code_check.core.ui.theme.YacTheme
import jp.co.yumemi.android.code_check.core.ui.theme.bold
import jp.co.yumemi.android.code_check.core.ui.theme.size

@Composable
internal fun RepositoryDetailTopSection(
    repositoryDetail: GhRepositoryDetail,
    language: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        UserNameItem(
            modifier = Modifier.fillMaxWidth(),
            userName = repositoryDetail.owner.login,
            avatarUrl = repositoryDetail.owner.avatarUrl,
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = repositoryDetail.name,
            style = MaterialTheme.typography.titleLarge.bold().size(24.sp),
            color = MaterialTheme.colorScheme.onSurface,
        )

        if (repositoryDetail.description != null) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = repositoryDetail.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        if (repositoryDetail.homepage != null) {
            LinkItem(
                modifier = Modifier.fillMaxWidth(),
                link = repositoryDetail.homepage,
                onClickLink = {},
            )
        }

        if (language != null) {
            LanguageItem(
                modifier = Modifier.fillMaxWidth(),
                language = language,
            )
        }

        CountInfoItem(
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Default.StarOutline,
            count = repositoryDetail.stargazersCount,
            unit = "Stars",
        )

        CountInfoItem(
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Outlined.ForkLeft,
            count = repositoryDetail.forksCount,
            unit = "Forks",
        )

        CountInfoItem(
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Outlined.RemoveRedEye,
            count = repositoryDetail.watchersCount,
            unit = "Watchers",
        )
    }
}

@Composable
private fun UserNameItem(
    userName: String,
    avatarUrl: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(20.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(avatarUrl)
                .build(),
            contentDescription = null,
        )

        Text(
            modifier = Modifier.weight(1f),
            text = userName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun LinkItem(
    link: String,
    onClickLink: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClickLink,
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Outlined.Link,
            contentDescription = "Link",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            text = link,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun CountInfoItem(
    icon: ImageVector,
    count: Int,
    unit: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Text(
                text = unit,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun LanguageItem(
    language: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Default.Language,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )


        Text(
            text = language,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@ComponentPreviews
@Composable
private fun RepositoryDetailTopSectionPreview(
    @PreviewParameter(GhRepositoryDetailPreviewParameter::class) repositoryDetail: GhRepositoryDetail,
) {
    YacTheme {
        RepositoryDetailTopSection(
            modifier = Modifier.fillMaxWidth(),
            repositoryDetail = repositoryDetail,
            language = "Kotlin",
        )
    }
}
