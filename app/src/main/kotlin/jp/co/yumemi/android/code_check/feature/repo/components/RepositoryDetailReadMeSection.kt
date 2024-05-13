package jp.co.yumemi.android.code_check.feature.repo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData

@Composable
internal fun RepositoryDetailReadMeSection(
    readMeHtml: String,
    modifier: Modifier = Modifier,
) {
    val webViewState = rememberWebViewStateWithHTMLData(readMeHtml)

    webViewState.webSettings.apply {
        isJavaScriptEnabled = true

        androidWebSettings.apply {
            domStorageEnabled = true
            isJavaScriptEnabled = true
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HorizontalDivider()

        ReadMeItem(
            modifier = Modifier.fillMaxWidth(),
        )

        WebView(
            modifier = modifier,
            state = webViewState,
        )
    }
}

@Composable
private fun ReadMeItem(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            modifier = Modifier.size(20.dp),
            imageVector = Icons.Outlined.Info,
            contentDescription = "Link",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Text(
            text = "ReadMe.md",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
