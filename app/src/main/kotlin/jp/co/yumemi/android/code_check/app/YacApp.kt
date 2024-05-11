package jp.co.yumemi.android.code_check.app

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import jp.co.yumemi.android.code_check.core.model.UserData
import jp.co.yumemi.android.code_check.core.ui.theme.MMTheme
import org.koin.compose.KoinContext

@Composable
fun YacApp(
    userData: UserData,
    windowWidthSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    KoinContext {
        MMTheme(
            themeConfig = userData.themeConfig,
            windowWidthSize = windowWidthSize,
        ) {
            Box(modifier) {
                Text(text = "Hello, YacApp!")
            }
        }
    }
}
