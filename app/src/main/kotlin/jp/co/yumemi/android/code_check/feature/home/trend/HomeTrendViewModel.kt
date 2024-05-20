package jp.co.yumemi.android.code_check.feature.home.trend

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.core.common.extensions.RateLimitException
import jp.co.yumemi.android.code_check.core.common.extensions.suspendRunCatching
import jp.co.yumemi.android.code_check.core.model.GhLanguage
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhTrendRepository
import jp.co.yumemi.android.code_check.core.model.GhTrendSince
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.model.updateWhenIdle
import jp.co.yumemi.android.code_check.core.repository.GhApiRepository
import jp.co.yumemi.android.code_check.core.repository.GhFavoriteRepository
import jp.co.yumemi.android.code_check.core.repository.UserDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.matsumo.yumemi.codecheck.R

class HomeTrendViewModel(
    private val userDataRepository: UserDataRepository,
    private val ghApiRepository: GhApiRepository,
    private val ghFavoriteRepository: GhFavoriteRepository,
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState<HomeTrendUiState>>(ScreenState.Loading)

    val screenState = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            ghFavoriteRepository.favoriteData.collectLatest { favorites ->
                _screenState.value = screenState.updateWhenIdle {
                    it.copy(favoriteRepoNames = favorites.repos)
                }
            }
        }
    }

    fun fetch() {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            _screenState.value = suspendRunCatching {
                val allLanguages = ghApiRepository.getLanguages()
                val selectedSince = GhTrendSince.from(userDataRepository.userData.first().trendSince)
                val selectedLanguage = userDataRepository.userData.first().trendLanguage.let {
                    allLanguages.find { lang -> lang.title == it }
                }

                HomeTrendUiState(
                    selectedSince = selectedSince,
                    selectedLanguage = selectedLanguage,
                    allLanguages = allLanguages,
                    trendingRepositories = ghApiRepository.searchTrendRepositories(selectedSince, selectedLanguage),
                    favoriteRepoNames = ghFavoriteRepository.favoriteData.first().repos,
                )
            }.fold(
                onSuccess = { ScreenState.Idle(it) },
                onFailure = { ScreenState.Error(if (it is RateLimitException) R.string.error_rate_limit else R.string.error_network) },
            )
        }
    }

    fun updateSetting(since: GhTrendSince, language: GhLanguage?) {
        viewModelScope.launch {
            userDataRepository.setTrendSince(since)
            userDataRepository.setTrendLanguage(language)
            fetch()
        }
    }

    fun addFavorite(repositoryName: GhRepositoryName) {
        viewModelScope.launch {
            ghFavoriteRepository.addFavoriteRepository(repositoryName)
        }
    }

    fun removeFavorite(repositoryName: GhRepositoryName) {
        viewModelScope.launch {
            ghFavoriteRepository.removeFavoriteRepository(repositoryName)
        }
    }

    suspend fun requestOgImageLink(repositoryName: GhRepositoryName): String {
        return ghApiRepository.getRepositoryOgImageLink(repositoryName)
    }
}

@Stable
data class HomeTrendUiState(
    val selectedSince: GhTrendSince,
    val selectedLanguage: GhLanguage?,
    val allLanguages: List<GhLanguage>,
    val trendingRepositories: List<GhTrendRepository>,
    val favoriteRepoNames: List<GhRepositoryName>,
)
