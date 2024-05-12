package jp.co.yumemi.android.code_check.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import jp.co.yumemi.android.code_check.core.extensions.LocalStateHolder
import jp.co.yumemi.android.code_check.core.extensions.StateHolder
import jp.co.yumemi.android.code_check.core.model.ThemeConfig
import jp.co.yumemi.android.code_check.core.ui.theme.color.DarkBlueColorScheme
import jp.co.yumemi.android.code_check.core.ui.theme.color.LightBlueColorScheme

@Composable
internal fun YacTheme(
    themeConfig: ThemeConfig = ThemeConfig.System,
    windowWidthSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    content: @Composable () -> Unit,
) {
    val shouldUseDarkTheme = shouldUseDarkTheme(themeConfig)

    CompositionLocalProvider(
        LocalWindowWidthSize provides windowWidthSize,
        LocalThemeConfig provides themeConfig,
        LocalStateHolder provides remember { StateHolder() },
        LocalSnackbarHostState provides remember { SnackbarHostState() },
    ) {
        MaterialTheme(
            colorScheme = if (shouldUseDarkTheme) DarkBlueColorScheme else LightBlueColorScheme,
            typography = LmsTypography,
            shapes = LmsShapes,
            content = content,
        )
    }
}

@Composable
fun shouldUseDarkTheme(themeConfig: ThemeConfig): Boolean {
    return when (themeConfig) {
        ThemeConfig.System -> isSystemInDarkTheme()
        ThemeConfig.Light -> false
        ThemeConfig.Dark -> true
    }
}

val LocalWindowWidthSize = staticCompositionLocalOf { WindowWidthSizeClass.Compact }
val LocalThemeConfig = staticCompositionLocalOf { ThemeConfig.System }
val LocalSnackbarHostState = staticCompositionLocalOf { SnackbarHostState() }
