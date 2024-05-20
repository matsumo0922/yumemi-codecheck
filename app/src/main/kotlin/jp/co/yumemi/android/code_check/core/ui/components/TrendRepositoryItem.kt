package jp.co.yumemi.android.code_check.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ForkLeft
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import jp.co.yumemi.android.code_check.core.common.extensions.toColor
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhTrendRepository
import jp.co.yumemi.android.code_check.core.ui.extensions.ComponentPreviews
import jp.co.yumemi.android.code_check.core.ui.previews.GhTrendRepositoryPreviewParameter
import jp.co.yumemi.android.code_check.core.ui.theme.YacTheme

@Composable
fun TrendRepositoryItem(
    trendRepository: GhTrendRepository,
    isFavorite: Boolean,
    onRequestOgImageLink: suspend (GhRepositoryName) -> String,
    onClickRepository: (GhRepositoryName) -> Unit,
    onClickAddFavorite: (GhRepositoryName) -> Unit,
    onClickRemoveFavorite: (GhRepositoryName) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isFavoriteCache by remember(isFavorite) { mutableStateOf(isFavorite) }
    var ogImageLink by rememberSaveable(trendRepository) { mutableStateOf("") }

    Logger.d("isFavorite: $isFavorite, isFavoriteCache: $isFavoriteCache")

    if (ogImageLink.isBlank()) {
        LaunchedEffect(trendRepository) {
            ogImageLink = onRequestOgImageLink.invoke(trendRepository.repoName)
        }
    }

    Card(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClickRepository.invoke(trendRepository.repoName) },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)),
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f),
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(ogImageLink)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
        ) {
            TitleItem(
                modifier = Modifier.fillMaxWidth(),
                trendRepository = trendRepository,
                isFavorite = isFavoriteCache,
                onClickAddFavorite = {
                    isFavoriteCache = true
                    onClickAddFavorite.invoke(it)
                },
                onClickRemoveFavorite = {
                    isFavoriteCache = false
                    onClickRemoveFavorite.invoke(it)
                },
            )

            if (trendRepository.description.isNotBlank()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = trendRepository.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (trendRepository.language != null && trendRepository.languageColor != null) {
                    LanguageCard(
                        language = trendRepository.language,
                        languageColor = trendRepository.languageColor.toColor(),
                    )
                }

                CountCard(
                    count = trendRepository.stars,
                    imageVector = Icons.Default.StarOutline,
                )

                CountCard(
                    count = trendRepository.forks,
                    imageVector = Icons.Default.ForkLeft,
                )
            }
        }
    }
}

@Composable
private fun TitleItem(
    trendRepository: GhTrendRepository,
    isFavorite: Boolean,
    onClickAddFavorite: (GhRepositoryName) -> Unit,
    onClickRemoveFavorite: (GhRepositoryName) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape),
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(trendRepository.avatar)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null,
        )

        Text(
            modifier = Modifier.weight(1f),
            text = trendRepository.repoName.toString(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        IconButton(
            onClick = {
                if (isFavorite) {
                    onClickRemoveFavorite.invoke(trendRepository.repoName)
                } else {
                    onClickAddFavorite.invoke(trendRepository.repoName)
                }
            },
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                contentDescription = "Favorite",
            )
        }
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
private fun CountCard(
    count: Int,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = imageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            text = count.toString(),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@ComponentPreviews
@Composable
private fun TrendRepositoryItemPreview(
    @PreviewParameter(GhTrendRepositoryPreviewParameter::class) trendRepository: GhTrendRepository,
) {
    YacTheme {
        TrendRepositoryItem(
            modifier = Modifier.fillMaxWidth(),
            trendRepository = trendRepository,
            isFavorite = false,
            onRequestOgImageLink = { "" },
            onClickRepository = {},
            onClickAddFavorite = {},
            onClickRemoveFavorite = {},
        )
    }
}
