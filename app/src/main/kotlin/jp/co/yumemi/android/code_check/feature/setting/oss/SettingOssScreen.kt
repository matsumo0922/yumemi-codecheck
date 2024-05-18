package jp.co.yumemi.android.code_check.feature.setting.oss

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.m3.LibraryDefaults
import jp.co.yumemi.android.code_check.feature.setting.SettingTopAppBar
import me.matsumo.yumemi.codecheck.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingOssScreen(
    terminate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state = rememberTopAppBarState()
    val behavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(state)

    Scaffold(
        modifier = modifier.nestedScroll(behavior.nestedScrollConnection),
        topBar = {
            SettingTopAppBar(
                title = stringResource(R.string.settings_oss_title),
                onClickBack = terminate,
                scrollBehavior = behavior,
            )
        },
    ) {
        LibrariesContainer(
            modifier = Modifier.fillMaxSize(),
            contentPadding = it,
            colors = LibraryDefaults.libraryColors(
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                badgeBackgroundColor = MaterialTheme.colorScheme.primary,
                badgeContentColor = MaterialTheme.colorScheme.onPrimary,
                dialogConfirmButtonColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }
}
