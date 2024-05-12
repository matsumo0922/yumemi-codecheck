package jp.co.yumemi.android.code_check.core.ui.extensions

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

@Composable
fun getAnnotatedString(targetStr: String, range: IntRange): AnnotatedString {
    if (range.first < 0 || range.last >= targetStr.length || range == 0..0) {
        return AnnotatedString(targetStr)
    }

    val startStr = targetStr.substring(0, (range.first).coerceAtLeast(0))
    val endStr = targetStr.substring((range.last + 1).coerceAtMost(targetStr.length))
    val annotatedStr = targetStr.substring(range)

    return buildAnnotatedString {
        append(startStr)

        withStyle(
            SpanStyle(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
            ),
        ) {
            append(annotatedStr)
        }

        append(endStr)
    }
}
