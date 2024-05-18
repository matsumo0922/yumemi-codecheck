package jp.co.yumemi.android.code_check.feature.setting.theme

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.core.model.ScreenState
import jp.co.yumemi.android.code_check.core.model.ThemeColorConfig
import jp.co.yumemi.android.code_check.core.model.ThemeConfig
import jp.co.yumemi.android.code_check.core.model.UserData
import jp.co.yumemi.android.code_check.core.repository.UserDataRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingThemeViewModel(
    private val userDataRepository: UserDataRepository,
) : ViewModel(){

    val screenState = userDataRepository.userData.map {
        ScreenState.Idle(
            SettingThemeUiState(it)
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = ScreenState.Loading,
        started = SharingStarted.WhileSubscribed(5000),
    )

    fun setThemeConfig(themeConfig: ThemeConfig) {
        viewModelScope.launch {
            userDataRepository.setThemeConfig(themeConfig)
        }
    }

    fun setThemeColorConfig(themeColorConfig: ThemeColorConfig) {
        viewModelScope.launch {
            userDataRepository.setThemeColorConfig(themeColorConfig)
        }
    }

    fun setUseDynamicColor(useDynamicColor: Boolean) {
        viewModelScope.launch {
            userDataRepository.setUseDynamicColor(useDynamicColor)
        }
    }
}

@Stable
data class SettingThemeUiState(
    val userData: UserData,
)
