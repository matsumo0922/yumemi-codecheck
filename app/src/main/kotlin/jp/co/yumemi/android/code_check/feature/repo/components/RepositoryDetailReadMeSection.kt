package jp.co.yumemi.android.code_check.feature.repo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.fleeksoft.ksoup.Ksoup
import com.multiplatform.webview.request.RequestInterceptor
import com.multiplatform.webview.request.WebRequest
import com.multiplatform.webview.request.WebRequestInterceptResult
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.WebViewNavigator
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewStateWithHTMLData
import jp.co.yumemi.android.code_check.core.common.extensions.colorToHexString

@Composable
internal fun RepositoryDetailReadMeSection(
    readMeHtml: String,
    onClickWeb: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val webViewState = rememberWebViewStateWithHTMLData(changeHtmlTextColor(readMeHtml))
    val webViewNavigator = rememberWebViewNavigator(
        requestInterceptor = object : RequestInterceptor {
            override fun onInterceptUrlRequest(request: WebRequest, navigator: WebViewNavigator): WebRequestInterceptResult {
                onClickWeb.invoke(request.url)
                return WebRequestInterceptResult.Reject
            }
        },
    )

    webViewState.webSettings.apply {
        isJavaScriptEnabled = true

        androidWebSettings.apply {
            domStorageEnabled = true
            isJavaScriptEnabled = true
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ReadMeItem(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth(),
        )

        WebView(
            modifier = Modifier.fillMaxWidth(),
            state = webViewState,
            navigator = webViewNavigator,
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

@Composable
private fun changeHtmlTextColor(html: String): String {
    val density = LocalDensity.current
    val document = Ksoup.parse(html)

    val paddingAttr = "padding: ${with(density) { 4.dp.toPx() }}px;"
    val colorAttr = "color: ${colorToHexString(MaterialTheme.colorScheme.onSurface)};"

    document.select("body").attr("style", "$colorAttr $paddingAttr")
    document.select("a").attr("style", colorAttr)

    return document.toString()
}
