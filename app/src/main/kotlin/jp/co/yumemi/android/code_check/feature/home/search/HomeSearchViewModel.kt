package jp.co.yumemi.android.code_check.feature.home.search

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import jp.co.yumemi.android.code_check.core.common.extensions.RateLimitException
import jp.co.yumemi.android.code_check.core.common.extensions.isAnyWordStartsWith
import jp.co.yumemi.android.code_check.core.common.extensions.suspendRunCatching
import jp.co.yumemi.android.code_check.core.model.GhLanguage
import jp.co.yumemi.android.code_check.core.model.GhOrder
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhRepositorySort
import jp.co.yumemi.android.code_check.core.model.GhSearchHistory
import jp.co.yumemi.android.code_check.core.model.GhSearchRepositories
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.model.updateWhenIdle
import jp.co.yumemi.android.code_check.core.repository.GhApiRepository
import jp.co.yumemi.android.code_check.core.repository.GhFavoriteRepository
import jp.co.yumemi.android.code_check.core.repository.GhSearchHistoryRepository
import jp.co.yumemi.android.code_check.core.ui.extensions.emptyPaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import me.matsumo.yumemi.codecheck.R

class HomeSearchViewModel(
    private val ghApiRepository: GhApiRepository,
    private val ghFavoriteRepository: GhFavoriteRepository,
    private val ghSearchHistoryRepository: GhSearchHistoryRepository,
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState<HomeSearchUiState>>(ScreenState.Loading)

    val screenState = _screenState.asStateFlow()

    init {
        viewModelScope.launch {
            ghSearchHistoryRepository.searchHistories.collectLatest {
                _screenState.value = screenState.updateWhenIdle { uiState ->
                    uiState.copy(
                        suggestions = it.filter { it.query.isAnyWordStartsWith(uiState.query) },
                        searchHistories = it,
                    )
                }
            }
        }

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
                HomeSearchUiState(
                    query = "",
                    suggestions = ghSearchHistoryRepository.searchHistories.first(),
                    searchHistories = ghSearchHistoryRepository.searchHistories.first(),
                    favoriteRepoNames = ghFavoriteRepository.favoriteData.first().repos,
                    searchRepositoriesPaging = emptyPaging(),
                    languages = ghApiRepository.getLanguages(),
                )
            }.fold(
                onSuccess = { ScreenState.Idle(it) },
                onFailure = { ScreenState.Error(if (it is RateLimitException) R.string.error_rate_limit else R.string.error_network) },
            )
        }
    }

    fun searchRepositories(
        query: String,
        sort: GhRepositorySort?,
        order: GhOrder?,
    ) {
        viewModelScope.launch {
            ghSearchHistoryRepository.addSearchHistory(query)

            _screenState.value = screenState.updateWhenIdle {
                it.copy(
                    searchRepositoriesPaging = ghApiRepository.getSearchRepositoriesPaging(query, sort, order),
                )
            }
        }
    }

    fun removeSearchHistory(searchHistory: GhSearchHistory) {
        viewModelScope.launch {
            ghSearchHistoryRepository.removeSearchHistory(searchHistory)
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

    fun updateQuery(query: String) {
        viewModelScope.launch {
            _screenState.value = screenState.updateWhenIdle { uiState ->
                uiState.copy(
                    query = query,
                    suggestions = uiState.searchHistories.filter { it.query.isAnyWordStartsWith(query) },
                    searchRepositoriesPaging = if (query.isEmpty()) emptyPaging() else uiState.searchRepositoriesPaging,
                )
            }
        }
    }
}

@Stable
data class HomeSearchUiState(
    val query: String,
    val suggestions: List<GhSearchHistory>,
    val searchHistories: List<GhSearchHistory>,
    val favoriteRepoNames: List<GhRepositoryName>,
    val searchRepositoriesPaging: Flow<PagingData<GhSearchRepositories.Item>>,
    val languages: List<GhLanguage>,
)
