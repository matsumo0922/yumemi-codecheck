package jp.co.yumemi.android.code_check.feature.home.search

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import jp.co.yumemi.android.code_check.core.extensions.suspendRunCatching
import jp.co.yumemi.android.code_check.core.model.GhOrder
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.model.GhRepositorySort
import jp.co.yumemi.android.code_check.core.model.GhUserDetail
import jp.co.yumemi.android.code_check.core.model.GhUserSort
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.model.SearchRepositories
import jp.co.yumemi.android.code_check.core.model.SearchUsers
import jp.co.yumemi.android.code_check.core.model.updateWhenIdle
import jp.co.yumemi.android.code_check.core.repository.GhApiRepository
import jp.co.yumemi.android.code_check.core.repository.GhFavoriteRepository
import jp.co.yumemi.android.code_check.core.ui.extensions.emptyPaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.matsumo.yumemi.codecheck.R

class HomeSearchViewModel(
    private val ghApiRepository: GhApiRepository,
    private val ghFavoriteRepository: GhFavoriteRepository,
) : ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState<HomeSearchUiState>>(ScreenState.Loading)

    val screenState = _screenState.asStateFlow()

    fun fetch() {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            _screenState.value = suspendRunCatching {
                HomeSearchUiState(
                    favoriteUsers = ghFavoriteRepository.getFavoriteUsers(),
                    favoriteRepositories = ghFavoriteRepository.getFavoriteRepositories(),
                    searchRepositoriesPaging = emptyPaging(),
                    searchUsersPaging = emptyPaging(),
                )
            }.fold(
                onSuccess = { ScreenState.Idle(it) },
                onFailure = { ScreenState.Error(R.string.error_executed) }
            )
        }
    }

    fun searchUsers(
        query: String,
        sort: GhUserSort?,
        order: GhOrder?,
    ) {
        viewModelScope.launch {
            _screenState.value = screenState.updateWhenIdle {
                it.copy(searchUsersPaging = ghApiRepository.getSearchUsersPaging(query, sort, order))
            }
        }
    }

    fun searchRepositories(
        query: String,
        sort: GhRepositorySort?,
        order: GhOrder?,
    ) {
        viewModelScope.launch {
            _screenState.value = screenState.updateWhenIdle {
                it.copy(searchRepositoriesPaging = ghApiRepository.getSearchRepositoriesPaging(query, sort, order))
            }
        }
    }
}

@Stable
data class HomeSearchUiState(
    val favoriteUsers: List<GhUserDetail>,
    val favoriteRepositories: List<GhRepositoryDetail>,
    val searchUsersPaging: Flow<PagingData<SearchUsers.Item>>,
    val searchRepositoriesPaging: Flow<PagingData<SearchRepositories.Item>>
)
