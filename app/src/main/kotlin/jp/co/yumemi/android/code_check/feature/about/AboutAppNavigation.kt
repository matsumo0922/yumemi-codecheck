package jp.co.yumemi.android.code_check.feature.about

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val AboutAppRoute = "aboutApp"

fun NavController.navigateToAboutApp() {
    this.navigate(AboutAppRoute)
}

fun NavGraphBuilder.aboutAppScreen(
    navigateToWeb: (String) -> Unit,
    terminate: () -> Unit,
) {
    composable(AboutAppRoute) {
        AboutAppScreen(
            modifier = Modifier.fillMaxSize(),
            navigateToWeb = navigateToWeb,
            terminate = terminate,
        )
    }
}
