package jp.co.yumemi.android.code_check.feature.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import jp.co.yumemi.android.code_check.core.ui.theme.center
import jp.co.yumemi.android.code_check.feature.about.components.AboutAppTopAppBar
import me.matsumo.yumemi.codecheck.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AboutAppScreen(
    navigateToWeb: (String) -> Unit,
    terminate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AboutAppTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                scrollBehavior = scrollBehavior,
                onClickDrawer = terminate,
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically,
            ),
        ) {
            Text(
                text = "Android Engineer Code Check",
                style = MaterialTheme.typography.titleLarge.center(),
                color = MaterialTheme.colorScheme.onSurface,
            )

            Text(
                text = stringResource(R.string.about_app_description),
                style = MaterialTheme.typography.bodyMedium.center(),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = { navigateToWeb.invoke("https://github.com/matsumo0922/yumemi-codecheck") },
            ) {
                Text(
                    text = "GitHub",
                )
            }
        }
    }
}
