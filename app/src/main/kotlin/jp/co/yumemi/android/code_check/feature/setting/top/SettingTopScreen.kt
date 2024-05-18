package jp.co.yumemi.android.code_check.feature.setting.top

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.yumemi.android.code_check.core.extensions.ToastUtils
import jp.co.yumemi.android.code_check.core.model.YacBuildConfig
import jp.co.yumemi.android.code_check.core.ui.AsyncLoadContents
import jp.co.yumemi.android.code_check.feature.setting.SettingTopAppBar
import jp.co.yumemi.android.code_check.feature.setting.top.components.SettingTopGeneralSection
import jp.co.yumemi.android.code_check.feature.setting.top.components.SettingTopInfoSection
import me.matsumo.yumemi.codecheck.R
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun SettingTopRoute(
    navigateToSettingTheme: () -> Unit,
    navigateToSettingOss: () -> Unit,
    terminate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingTopViewModel = koinViewModel(),
) {
    val context = LocalContext.current
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    AsyncLoadContents(
        modifier = modifier,
        screenState = screenState,
    ) {
        SettingTopScreen(
            modifier = Modifier.fillMaxSize(),
            buildConfig = it.buildConfig,
            onClickSettingTheme = navigateToSettingTheme,
            onClickOpenSourceLicense = navigateToSettingOss,
            onClickClearFavorites = {
                viewModel.clearFavorites()
                ToastUtils.show(context, R.string.settings_top_general_clear_favorite_toast)
            },
            onClickClearSearchHistory = {
                viewModel.clearSearchHistory()
                ToastUtils.show(context, R.string.settings_top_general_clear_search_history_toast)
            },
            onClickBack = terminate,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingTopScreen(
    buildConfig: YacBuildConfig,
    onClickSettingTheme: () -> Unit,
    onClickOpenSourceLicense: () -> Unit,
    onClickClearFavorites: () -> Unit,
    onClickClearSearchHistory: () -> Unit,
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
                SettingTopGeneralSection(
                    modifier = Modifier.fillMaxWidth(),
                    onClickSettingTheme = onClickSettingTheme,
                    onClickClearFavorites = onClickClearFavorites,
                    onClickClearSearchHistory = onClickClearSearchHistory,
                )
            }

            item {
                SettingTopInfoSection(
                    buildConfig = buildConfig,
                    onClickOpenSourceLicense = onClickOpenSourceLicense,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }
}
