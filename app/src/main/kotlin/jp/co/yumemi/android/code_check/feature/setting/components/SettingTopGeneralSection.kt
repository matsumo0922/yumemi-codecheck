package jp.co.yumemi.android.code_check.feature.setting.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.co.yumemi.android.code_check.core.ui.components.SettingTextItem
import me.matsumo.yumemi.codecheck.R

@Composable
internal fun SettingTopGeneralSection(
    onClickClearFavorites: () -> Unit,
    onCLickCache: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        SettingTopTitleItem(
            modifier = Modifier.fillMaxWidth(),
            text = R.string.setting_top_general,
        )

        SettingTextItem(
            modifier = Modifier.fillMaxWidth(),
            title = R.string.setting_top_theme_app,
            description = R.string.setting_top_theme_app_description,
            onClick = onClickAppTheme,
        )
    }
}
