package jp.co.yumemi.android.code_check.feature.setting.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.yumemi.android.code_check.core.model.ThemeColorConfig
import jp.co.yumemi.android.code_check.core.model.ThemeConfig
import jp.co.yumemi.android.code_check.core.model.UserData
import jp.co.yumemi.android.code_check.core.ui.AsyncLoadContents
import jp.co.yumemi.android.code_check.core.ui.components.SettingSwitchItem
import jp.co.yumemi.android.code_check.feature.setting.SettingTopAppBar
import jp.co.yumemi.android.code_check.feature.setting.theme.components.SettingThemeColorSection
import jp.co.yumemi.android.code_check.feature.setting.theme.components.SettingThemeTabsSection
import me.matsumo.yumemi.codecheck.R
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingThemeRoute(
    terminate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingThemeViewModel = koinViewModel(),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
    ) {
        SettingThemeScreen(
            modifier = Modifier.fillMaxSize(),
            userData = it.userData,
            onSelectTheme = viewModel::setThemeConfig,
            onSelectThemeColor = viewModel::setThemeColorConfig,
            onClickDynamicColor = viewModel::setUseDynamicColor,
            onClickBack = terminate,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingThemeScreen(
    userData: UserData,
    onSelectTheme: (ThemeConfig) -> Unit,
    onSelectThemeColor: (ThemeColorConfig) -> Unit,
    onClickDynamicColor: (Boolean) -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberTopAppBarState()
    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(state)

    Scaffold(
        modifier = modifier.nestedScroll(behavior.nestedScrollConnection),
        topBar = {
            SettingTopAppBar(
                title = stringResource(R.string.settings_title),
                onClickBack = onClickBack,
                scrollBehavior = behavior,
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .fillMaxSize(),
            contentPadding = it,
        ) {
            item {
                SettingThemeTabsSection(
                    modifier = Modifier.fillMaxWidth(),
                    themeConfig = userData.themeConfig,
                    onSelectTheme = onSelectTheme,
                )
            }

            item {
                SettingSwitchItem(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    title = R.string.settings_theme_theme_dynamic_color,
                    description = R.string.settings_theme_theme_dynamic_color_description,
                    value = userData.isUseDynamicColor,
                    onValueChanged = onClickDynamicColor,
                )
            }

            item {
                SettingThemeColorSection(
                    modifier = Modifier.fillMaxWidth(),
                    isUseDynamicColor = userData.isUseDynamicColor,
                    themeConfig = userData.themeConfig,
                    themeColorConfig = userData.themeColorConfig,
                    onSelectThemeColor = onSelectThemeColor,
                )
            }
        }
    }
}
