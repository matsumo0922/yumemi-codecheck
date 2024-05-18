package jp.co.yumemi.android.code_check.feature.setting.top

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.model.UserData
import jp.co.yumemi.android.code_check.core.model.YacBuildConfig
import jp.co.yumemi.android.code_check.core.repository.GhFavoriteRepository
import jp.co.yumemi.android.code_check.core.repository.GhSearchHistoryRepository
import jp.co.yumemi.android.code_check.core.repository.UserDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SettingTopViewModel(
    private val userDataRepository: UserDataRepository,
    private val ghFavoriteRepository: GhFavoriteRepository,
    private val ghSearchHistoryRepository: GhSearchHistoryRepository,
    private val buildConfig: YacBuildConfig,
) : ViewModel() {

    val screenState = userDataRepository.userData.map {
        ScreenState.Idle(
            SettingTopUiState(
                userData = it,
                buildConfig = buildConfig,
            ),
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ScreenState.Loading,
    )

    fun clearFavorites() {
        ghFavoriteRepository.clear()
    }

    fun clearSearchHistory() {
        ghSearchHistoryRepository.clear()
    }
}

@Stable
data class SettingTopUiState(
    val userData: UserData,
    val buildConfig: YacBuildConfig,
)
