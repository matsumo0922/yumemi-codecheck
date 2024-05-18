package jp.co.yumemi.android.code_check.feature.setting.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.core.model.ThemeColorConfig
import jp.co.yumemi.android.code_check.core.model.ThemeConfig
import jp.co.yumemi.android.code_check.core.ui.components.SettingTextItem
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
import me.matsumo.yumemi.codecheck.R

@Composable
internal fun SettingThemeColorSection(
    isUseDynamicColor: Boolean,
    themeConfig: ThemeConfig,
    themeColorConfig: ThemeColorConfig,
    onSelectThemeColor: (ThemeColorConfig) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isDarkMode = when (themeConfig) {
        ThemeConfig.Light -> false
        ThemeConfig.Dark -> true
        else -> isSystemInDarkTheme()
    }

    Column(modifier) {
        SettingTextItem(
            modifier = Modifier.fillMaxWidth(),
            title = R.string.settings_theme_color,
            description = R.string.settings_theme_color_description,
            onClick = { /* do nothing */ },
            isEnabled = !isUseDynamicColor,
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            items(
                items = ThemeColorConfig.entries.toTypedArray(),
                key = { it.name },
            ) {
                val color = when (it) {
                    ThemeColorConfig.Blue -> if (isDarkMode) DarkBlueColorScheme else LightBlueColorScheme
                    ThemeColorConfig.Brown -> if (isDarkMode) DarkBrownColorScheme else LightBrownColorScheme
                    ThemeColorConfig.Green -> if (isDarkMode) DarkGreenColorScheme else LightGreenColorScheme
                    ThemeColorConfig.Purple -> if (isDarkMode) DarkPurpleColorScheme else LightPurpleColorScheme
                    ThemeColorConfig.Pink -> if (isDarkMode) DarkPinkColorScheme else LightPinkColorScheme
                }

                SettingThemeColorItem(
                    onClick = { onSelectThemeColor.invoke(it) },
                    isEnabled = !isUseDynamicColor,
                    isSelected = it == themeColorConfig,
                    backgroundColor = color.surfaceVariant,
                    primaryColor = color.primary,
                    secondaryColor = color.secondary,
                    tertiaryColor = color.tertiary,
                )
            }
        }
    }
}

@Composable
private fun SettingThemeColorItem(
    isEnabled: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
    backgroundColor: Color,
    primaryColor: Color,
    secondaryColor: Color,
    tertiaryColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .size(96.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = if (isSelected && isEnabled) tertiaryColor else Color.Transparent,
                shape = RoundedCornerShape(8.dp),
            )
            .background(backgroundColor.copy(alpha = if (isSelected && isEnabled) 1f else 0.5f))
            .clickable(
                enabled = isEnabled,
                onClick = onClick,
            ),
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(16.dp)
                .clip(CircleShape),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(primaryColor.copy(alpha = if (isEnabled) 1f else 0.5f)),
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(secondaryColor.copy(alpha = if (isEnabled) 1f else 0.5f)),
            )
        }
    }
}
