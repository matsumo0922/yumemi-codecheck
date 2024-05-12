package jp.co.yumemi.android.code_check.feature.home.favorite

import androidx.lifecycle.ViewModel
import jp.co.yumemi.android.code_check.core.repository.GitHubRepository

@Suppress("detekt.all")
class HomeFavoriteViewModel(
    private val gitHubRepository: GitHubRepository,
) : ViewModel()
