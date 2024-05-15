package jp.co.yumemi.android.code_check.feature.home.trend

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.core.extensions.RateLimitException
import jp.co.yumemi.android.code_check.core.extensions.suspendRunCatching
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhTrendRepository
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.model.updateWhenIdle
import jp.co.yumemi.android.code_check.core.repository.GhApiRepository
import jp.co.yumemi.android.code_check.core.repository.GhFavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.matsumo.yumemi.codecheck.R

class HomeTrendViewModel(
    private val ghApiRepository: GhApiRepository,
    private val ghFavoriteRepository: GhFavoriteRepository
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
                HomeTrendUiState(
                    trendingRepositories = ghApiRepository.searchTrendRepositories(),
                    favoriteRepoNames = ghFavoriteRepository.favoriteData.first().repos,
                )
            }.fold(
                onSuccess = { ScreenState.Idle(it) },
                onFailure = { ScreenState.Error(if (it is RateLimitException) R.string.error_rate_limit else R.string.error_network) },
            )
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
    val trendingRepositories: List<GhTrendRepository>,
    val favoriteRepoNames: List<GhRepositoryName>,
)
