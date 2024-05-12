package jp.co.yumemi.android.code_check.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhSearchRepositories
import jp.co.yumemi.android.code_check.core.ui.extensions.getAnnotatedString
import jp.co.yumemi.android.code_check.core.ui.theme.size
import kotlinx.collections.immutable.toImmutableList
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import me.matsumo.yumemi.codecheck.R
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
internal fun SearchRepositoryItem(
    isFavorite: Boolean,
    item: GhSearchRepositories.Item,
    onClickRepository: (GhRepositoryName) -> Unit,
    onClickAddFavorite: (GhRepositoryName) -> Unit,
    onClickRemoveFavorite: (GhRepositoryName) -> Unit,
    modifier: Modifier = Modifier,
    markupRange: IntRange? = null,
    languageColor: Color? = null,
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClickRepository.invoke(item.repoName) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            TitleSection(
                modifier = Modifier.fillMaxWidth(),
                isFavorite = isFavorite,
                item = item,
                markupRange = markupRange,
                onClickAddFavorite = onClickAddFavorite,
                onClickRemoveFavorite = onClickRemoveFavorite,
            )

            if (item.description != null) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }

            if (item.topics.isNotEmpty()) {
                TopicItems(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth(),
                    topics = item.topics.take(5).toImmutableList(),
                    onClickTag = {},
                )
            }

            InfoSection(
                modifier = Modifier.fillMaxWidth(),
                item = item,
                languageColor = languageColor,
            )
        }
    }
}

@Composable
private fun TitleSection(
    isFavorite: Boolean,
    item: GhSearchRepositories.Item,
    markupRange: IntRange?,
    onClickAddFavorite: (GhRepositoryName) -> Unit,
    onClickRemoveFavorite: (GhRepositoryName) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(item.owner.avatarUrl)
                .build(),
            contentDescription = null,
        )

        Text(
            modifier = Modifier.weight(1f),
            text = getAnnotatedString(item.repoName.toString(), markupRange ?: 0..0),
            style = MaterialTheme.typography.bodyMedium.size(18.sp),
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        IconButton(
            onClick = {
                if (isFavorite) {
                    onClickRemoveFavorite.invoke(item.repoName)
                } else {
                    onClickAddFavorite.invoke(item.repoName)
                }
            }
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite",
            )
        }
    }
}

@Composable
private fun InfoSection(
    item: GhSearchRepositories.Item,
    languageColor: Color?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        if (item.language != null) {
            LanguageCard(
                language = item.language,
                languageColor = languageColor,
            )
        }

        StarCountCard(
            count = item.stargazersCount,
        )

        Text(
            text = "Updated on ${item.updatedAt.toRelativeTimeString()}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun LanguageCard(
    language: String,
    languageColor: Color?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(CircleShape)
                .background(languageColor ?: Color.Gray),
        )

        Text(
            text = language,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun StarCountCard(
    count: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = Icons.Outlined.StarOutline,
            contentDescription = "Star",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun Instant.toRelativeTimeString(): String {
    val now = Clock.System.now()
    val duration = now - this

    return when {
        duration.inWholeDays > 0 -> {
            if (duration.inWholeDays < 7) {
                stringResource(R.string.unit_day_before, duration.inWholeDays)
            } else {
                LocalDateTime
                    .ofInstant(this.toJavaInstant(), ZoneId.of("UTC"))
                    .format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
            }
        }

        duration.inWholeHours > 0 -> stringResource(R.string.unit_hour_before, duration.inWholeHours)
        duration.inWholeMinutes > 0 -> stringResource(R.string.unit_minute_before, duration.inWholeMinutes)
        else -> stringResource(R.string.unit_second_before, duration.inWholeSeconds)
    }
}
