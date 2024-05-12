package jp.co.yumemi.android.code_check.core.ui.extensions

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "1. Light",
    group = "Component",
    showBackground = true,
    backgroundColor = 0xFFF8F6F2,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "2. Dark",
    group = "Component",
    showBackground = true,
    backgroundColor = 0xFF0F0F0F,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
annotation class ComponentPreviews

@Preview(
    name = "1. Light",
    group = "Screen",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Preview(
    name = "2. Dark",
    group = "Screen",
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
annotation class ScreenPreviews
