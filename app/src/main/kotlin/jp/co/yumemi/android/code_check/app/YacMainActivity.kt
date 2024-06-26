package jp.co.yumemi.android.code_check.app

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.yumemi.android.code_check.app.ui.YacApp
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.ui.AsyncLoadContents
import jp.co.yumemi.android.code_check.core.ui.theme.shouldUseDarkTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class YacMainActivity : ComponentActivity(), KoinComponent {

    private val viewModel by viewModel<YacMainViewModel>()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val screenState by viewModel.screenState.collectAsStateWithLifecycle()

            AsyncLoadContents(
                modifier = Modifier.fillMaxSize(),
                screenState = screenState,
            ) { uiState ->
                val windowSize = calculateWindowSizeClass(this)
                val isDarkTheme = shouldUseDarkTheme(uiState.userData.themeConfig)

                val lightScrim = Color.argb(0xe6, 0xFF, 0xFF, 0xFF)
                val darkScrim = Color.argb(0x80, 0x1b, 0x1b, 0x1b)

                DisposableEffect(isDarkTheme) {
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.auto(Color.TRANSPARENT, Color.TRANSPARENT) { isDarkTheme },
                        navigationBarStyle = SystemBarStyle.auto(lightScrim, darkScrim) { isDarkTheme },
                    )
                    onDispose {}
                }

                YacApp(
                    modifier = Modifier.fillMaxSize(),
                    userData = uiState.userData,
                    windowWidthSize = windowSize.widthSizeClass,
                )
            }

            splashScreen.setKeepOnScreenCondition { screenState is ScreenState.Loading }
        }
    }
}
