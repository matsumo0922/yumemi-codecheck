package jp.co.yumemi.android.code_check.feature.repo.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail

@Composable
internal fun RepositoryDetailExpandedScreen(
    repositoryDetail: GhRepositoryDetail,
    readMeHtml: String,
    language: String?,
    contentPadding: PaddingValues,
    onClickWeb: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(contentPadding),
        verticalAlignment = Alignment.Top,
    ) {
        RepositoryDetailTopSection(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            repositoryDetail = repositoryDetail,
            language = language,
            onClickLink = onClickWeb,
        )

        VerticalDivider()

        RepositoryDetailReadMeSection(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            readMeHtml = readMeHtml,
            onClickWeb = onClickWeb,
        )
    }
}
