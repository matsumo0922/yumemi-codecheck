package jp.co.yumemi.android.code_check.feature.setting.top.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.co.yumemi.android.code_check.core.ui.components.SettingTextItem
import me.matsumo.yumemi.codecheck.R

@Composable
internal fun SettingTopGeneralSection(
    onClickSettingTheme: () -> Unit,
    onClickClearFavorites: () -> Unit,
    onClickClearSearchHistory: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        SettingTopTitleItem(
            modifier = Modifier.fillMaxWidth(),
            text = R.string.settings_top_general,
        )

        SettingTextItem(
            modifier = Modifier.fillMaxWidth(),
            title = R.string.settings_top_general_theme_app,
            description = R.string.settings_top_general_theme_app_description,
            onClick = onClickSettingTheme,
        )

        SettingTextItem(
            modifier = Modifier.fillMaxWidth(),
            title = R.string.settings_top_general_clear_favorite,
            description = R.string.settings_top_general_clear_favorite_description,
            onClick = onClickClearFavorites,
        )

        SettingTextItem(
            modifier = Modifier.fillMaxWidth(),
            title = R.string.settings_top_general_clear_search_history,
            description = R.string.settings_top_general_clear_search_history_description,
            onClick = onClickClearSearchHistory,
        )
    }
}
