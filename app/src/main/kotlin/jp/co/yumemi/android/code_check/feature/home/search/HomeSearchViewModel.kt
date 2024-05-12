package jp.co.yumemi.android.code_check.feature.home.search

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import jp.co.yumemi.android.code_check.core.extensions.isAnyWordStartsWith
import jp.co.yumemi.android.code_check.core.extensions.suspendRunCatching
import jp.co.yumemi.android.code_check.core.model.GhOrder
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.model.GhRepositorySort
import jp.co.yumemi.android.code_check.core.model.GhSearchHistory
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.model.GhSearchRepositories
import jp.co.yumemi.android.code_check.core.model.updateWhenIdle
import jp.co.yumemi.android.code_check.core.repository.GhApiRepository
import jp.co.yumemi.android.code_check.core.repository.GhFavoriteRepository
import jp.co.yumemi.android.code_check.core.repository.GhSearchHistoryRepository
import jp.co.yumemi.android.code_check.core.ui.extensions.emptyPaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun fetch() {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            _screenState.value = suspendRunCatching {
                HomeSearchUiState(
                    query = "",
                    suggestions = ghSearchHistoryRepository.searchHistories.first(),
                    searchHistories = ghSearchHistoryRepository.searchHistories.first(),
                    favoriteRepositories = ghFavoriteRepository.getFavoriteRepositories(),
                    searchRepositoriesPaging = emptyPaging(),
                    languageColors = ghApiRepository.getLanguageColors()
                )
            }.fold(
                onSuccess = { ScreenState.Idle(it) },
                onFailure = { ScreenState.Error(R.string.error_executed) }
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
                it.copy(searchRepositoriesPaging = ghApiRepository.getSearchRepositoriesPaging(query, sort, order))
            }
        }
    }

    fun removeSearchHistory(searchHistory: GhSearchHistory) {
        viewModelScope.launch {
            ghSearchHistoryRepository.removeSearchHistory(searchHistory)
        }
    }

    fun updateQuery(query: String) {
        viewModelScope.launch {
            _screenState.value = screenState.updateWhenIdle { uiState ->
                uiState.copy(
                    query = query,
                    suggestions = uiState.searchHistories.filter { it.query.isAnyWordStartsWith(query) },
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
    val favoriteRepositories: List<GhRepositoryDetail>,
    val searchRepositoriesPaging: Flow<PagingData<GhSearchRepositories.Item>>,
    val languageColors: Map<String, Color?>,
)
