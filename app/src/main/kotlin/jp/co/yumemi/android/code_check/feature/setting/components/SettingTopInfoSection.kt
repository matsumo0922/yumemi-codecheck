package jp.co.yumemi.android.code_check.feature.setting.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import jp.co.yumemi.android.code_check.core.model.YacBuildConfig
import jp.co.yumemi.android.code_check.core.ui.components.SettingTextItem
import me.matsumo.yumemi.codecheck.R

@Composable
internal fun SettingTopInfoSection(
    buildConfig: YacBuildConfig,
    onClickOpenSourceLicense: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val clipboard = LocalClipboardManager.current

    Column(modifier) {
        SettingTopTitleItem(
            modifier = Modifier.fillMaxWidth(),
            text = R.string.settings_top_information,
        )

        SettingTextItem(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.settings_top_information_version),
            description = "${buildConfig.versionName}:${buildConfig.versionCode}",
            onLongClick = { clipboard.setText(AnnotatedString("${buildConfig.versionName}:${buildConfig.versionName}")) },
        )

        SettingTextItem(
            modifier = Modifier.fillMaxWidth(),
            title = R.string.settings_top_information_open_source_license,
            description = R.string.settings_top_information_open_source_license_description,
            onClick = { onClickOpenSourceLicense.invoke() },
        )
    }
}
