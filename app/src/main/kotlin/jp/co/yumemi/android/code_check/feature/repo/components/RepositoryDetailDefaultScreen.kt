package jp.co.yumemi.android.code_check.feature.repo.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail

@Composable
internal fun RepositoryDetailDefaultScreen(
    repositoryDetail: GhRepositoryDetail,
    readMeHtml: String,
    language: String?,
    contentPadding: PaddingValues,
    onClickWeb: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        item {
            RepositoryDetailTopSection(
                modifier = Modifier.fillMaxWidth(),
                repositoryDetail = repositoryDetail,
                language = language,
                onClickLink = onClickWeb,
            )
        }

        item {
            HorizontalDivider()
        }

        item {
            RepositoryDetailReadMeSection(
                modifier = Modifier.fillMaxWidth(),
                readMeHtml = readMeHtml,
                onClickWeb = onClickWeb,
            )
        }
    }
}
