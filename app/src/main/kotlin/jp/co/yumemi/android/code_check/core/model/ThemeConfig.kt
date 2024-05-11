package jp.co.yumemi.android.code_check.core.model

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

enum class ThemeConfig {
    System, Light, Dark
}

@Composable
fun ThemeConfig.isDark(): Boolean {
    return when (this) {
        ThemeConfig.System -> isSystemInDarkTheme()
        ThemeConfig.Light -> false
        ThemeConfig.Dark -> true
    }
}
