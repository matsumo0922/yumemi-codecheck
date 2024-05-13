package jp.co.yumemi.android.code_check.feature.repo

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName

const val RepositoryOwner = "repositoryOwner"
const val RepositoryName = "repositoryName"
const val RepositoryDetailRoute = "repositoryDetail/{$RepositoryOwner}/{$RepositoryName}"

fun NavController.navigateToRepositoryDetail(repositoryName: GhRepositoryName) {
    this.navigate("repositoryDetail/${repositoryName.owner}/${repositoryName.name}")
}

fun NavGraphBuilder.repositoryDetailScreen() {
    composable(
        route = RepositoryDetailRoute,
        arguments = listOf(
            navArgument(RepositoryOwner) { type = NavType.StringType },
            navArgument(RepositoryName) { type = NavType.StringType },
        ),
    ) {
        RepositoryDetailRoute(
            repositoryName = GhRepositoryName(
                owner = it.arguments?.getString(RepositoryOwner).orEmpty(),
                name = it.arguments?.getString(RepositoryName).orEmpty(),
            ),
        )
    }
}
