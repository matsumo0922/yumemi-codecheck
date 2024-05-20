package jp.co.yumemi.android.code_check.core.ui.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import jp.co.yumemi.android.code_check.core.common.extensions.LocalStateHolder
import jp.co.yumemi.android.code_check.core.common.extensions.StateHolder
import jp.co.yumemi.android.code_check.core.model.ThemeColorConfig
import jp.co.yumemi.android.code_check.core.model.ThemeConfig
import jp.co.yumemi.android.code_check.core.ui.theme.color.DarkBlueColorScheme
import jp.co.yumemi.android.code_check.core.ui.theme.color.DarkBrownColorScheme
import jp.co.yumemi.android.code_check.core.ui.theme.color.DarkGreenColorScheme
import jp.co.yumemi.android.code_check.core.ui.theme.color.DarkPinkColorScheme
import jp.co.yumemi.android.code_check.core.ui.theme.color.DarkPurpleColorScheme
import jp.co.yumemi.android.code_check.core.ui.theme.color.LightBlueColorScheme
import jp.co.yumemi.android.code_check.core.ui.theme.color.LightBrownColorScheme
import jp.co.yumemi.android.code_check.core.ui.theme.color.LightGreenColorScheme
import jp.co.yumemi.android.code_check.core.ui.theme.color.LightPinkColorScheme
import jp.co.yumemi.android.code_check.core.ui.theme.color.LightPurpleColorScheme

@Composable
internal fun YacTheme(
    themeConfig: ThemeConfig = ThemeConfig.System,
    themeColorConfig: ThemeColorConfig = ThemeColorConfig.Blue,
    enableDynamicTheme: Boolean = false,
    windowWidthSize: WindowWidthSizeClass = WindowWidthSizeClass.Compact,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val shouldUseDarkTheme = shouldUseDarkTheme(themeConfig)

    val colorScheme = when {
        enableDynamicTheme && supportsDynamicTheming() -> if (shouldUseDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        else -> when (themeColorConfig) {
            ThemeColorConfig.Blue -> if (shouldUseDarkTheme) DarkBlueColorScheme else LightBlueColorScheme
            ThemeColorConfig.Brown -> if (shouldUseDarkTheme) DarkBrownColorScheme else LightBrownColorScheme
            ThemeColorConfig.Green -> if (shouldUseDarkTheme) DarkGreenColorScheme else LightGreenColorScheme
            ThemeColorConfig.Purple -> if (shouldUseDarkTheme) DarkPurpleColorScheme else LightPurpleColorScheme
            ThemeColorConfig.Pink -> if (shouldUseDarkTheme) DarkPinkColorScheme else LightPinkColorScheme
        }
    }

    CompositionLocalProvider(
        LocalWindowWidthSize provides windowWidthSize,
        LocalThemeConfig provides themeConfig,
        LocalStateHolder provides remember { StateHolder() },
        LocalSnackbarHostState provides remember { SnackbarHostState() },
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
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

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
private fun supportsDynamicTheming(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}

val LocalWindowWidthSize = staticCompositionLocalOf { WindowWidthSizeClass.Compact }
val LocalThemeConfig = staticCompositionLocalOf { ThemeConfig.System }
val LocalSnackbarHostState = staticCompositionLocalOf { SnackbarHostState() }
