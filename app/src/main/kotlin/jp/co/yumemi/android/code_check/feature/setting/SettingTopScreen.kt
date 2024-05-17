package jp.co.yumemi.android.code_check.feature.setting

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.yumemi.android.code_check.core.model.YacBuildConfig
import jp.co.yumemi.android.code_check.core.ui.AsyncLoadContents
import jp.co.yumemi.android.code_check.feature.setting.components.SettingTopAppBar
import jp.co.yumemi.android.code_check.feature.setting.components.SettingTopInfoSection
import jp.co.yumemi.android.code_check.feature.setting.components.SettingTopThemeSection
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingTopRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingTopViewModel = koinViewModel(),
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingTopScreen(
    buildConfig: YacBuildConfig,
    onClickSettingTheme: () -> Unit,
    onClickClearFavorites: () -> Unit,
    onClickClearCache: () -> Unit,
    onClickBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberTopAppBarState()
    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(state)

    Scaffold(
        modifier = modifier.nestedScroll(behavior.nestedScrollConnection),
        topBar = {
            SettingTopAppBar(
                onClickBack = onClickBack,
                scrollBehavior = behavior,
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = it,
        ) {
            item {
                SettingTopThemeSection(
                    modifier = Modifier.fillMaxWidth(),
                    onClickSettingTheme = onClickSettingTheme,
                )
            }

            item {
                SettingTopInfoSection(
                    buildConfig = buildConfig,
                    onClickOpenSourceLicense = {},
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
