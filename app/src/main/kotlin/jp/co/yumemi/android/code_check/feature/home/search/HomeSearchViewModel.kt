package jp.co.yumemi.android.code_check.feature.home.search

import androidx.lifecycle.ViewModel
import jp.co.yumemi.android.code_check.core.repository.GitHubRepository

@Suppress("detekt.all")
class HomeSearchViewModel(
    private val gitHubRepository: GitHubRepository,
) : ViewModel()
