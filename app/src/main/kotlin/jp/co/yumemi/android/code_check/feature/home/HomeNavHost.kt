package jp.co.yumemi.android.code_check.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.feature.home.favorite.homeScreenFavorite
import jp.co.yumemi.android.code_check.feature.home.search.HomeSearchRoute
import jp.co.yumemi.android.code_check.feature.home.search.homeSearchScreen

@Composable
fun HomeNavHost(
    openDrawer: () -> Unit,
    navigateToRepositoryDetail: (GhRepositoryName) -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeSearchRoute,
    ) {
        homeSearchScreen(
            openDrawer = openDrawer,
            navigateToRepositoryDetail = navigateToRepositoryDetail,
        )

        homeScreenFavorite(
            openDrawer = openDrawer,
        )
    }
}
