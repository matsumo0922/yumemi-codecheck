package jp.co.yumemi.android.code_check.feature.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HomeRoute = "home"

fun NavController.navigateToHome() {
    this.navigate(HomeRoute)
}

fun NavGraphBuilder.homeScreen() {
    composable(HomeRoute) {
        HomeScreen()
    }
}