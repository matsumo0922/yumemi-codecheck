package jp.co.yumemi.android.code_check.feature.repo

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.core.extensions.suspendRunCatching
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.repository.GhApiRepository
import jp.co.yumemi.android.code_check.core.repository.GhFavoriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.matsumo.yumemi.codecheck.R

class RepositoryDetailViewModel(
    private val ghApiRepository: GhApiRepository,
    private val ghFavoriteRepository: GhFavoriteRepository,
): ViewModel() {

    private val _screenState = MutableStateFlow<ScreenState<RepositoryDetailUiState>>(ScreenState.Loading)

    val screenState = _screenState.asStateFlow()

    fun fetch(repositoryName: GhRepositoryName) {
        viewModelScope.launch {
            _screenState.value = ScreenState.Loading
            _screenState.value = suspendRunCatching {
                RepositoryDetailUiState(
                    isFavorite = ghFavoriteRepository.isFavoriteRepository(repositoryName),
                    repositoryDetail = ghApiRepository.getRepositoryDetail(repositoryName),
                    languageColors = ghApiRepository.getLanguageColors(),
                )
            }.fold(
                onSuccess = { ScreenState.Idle(it) },
                onFailure = { ScreenState.Error(R.string.error_executed) },
            )
        }
    }
}

@Stable
data class RepositoryDetailUiState(
    val isFavorite: Boolean,
    val repositoryDetail: GhRepositoryDetail,
    val languageColors: Map<String, Color?>,
)
