package jp.co.yumemi.android.code_check.app

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.model.UserData
import jp.co.yumemi.android.code_check.core.repository.UserDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class YacMainViewModel(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {

    val screenState = userDataRepository.userData.map {
        ScreenState.Idle(
            YacMainUiState(
                userData = it,
            ),
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = ScreenState.Loading,
        started = SharingStarted.WhileSubscribed(5000),
    )
}

@Stable
data class YacMainUiState(
    val userData: UserData,
)
