package jp.co.yumemi.android.code_check.app.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.co.yumemi.android.code_check.core.model.UserData
import jp.co.yumemi.android.code_check.core.ui.theme.YacTheme
import org.koin.compose.KoinContext

@Composable
fun YacApp(
    userData: UserData,
    windowWidthSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    KoinContext {
        YacTheme(
            themeConfig = userData.themeConfig,
            windowWidthSize = windowWidthSize,
        ) {
            YacNavHost(
                modifier = modifier,
            )
        }
    }
}
