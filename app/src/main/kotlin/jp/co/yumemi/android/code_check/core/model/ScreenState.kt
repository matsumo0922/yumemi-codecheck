package jp.co.yumemi.android.code_check.core.model

import androidx.compose.runtime.Stable
import kotlinx.coroutines.flow.StateFlow

@Stable
sealed interface ScreenState<out T> {
    data object Loading : ScreenState<Nothing>

    data class Error(
        val message: Int,
        val retryTitle: Int? = null,
    ) : ScreenState<Nothing>

    data class Idle<T>(
        var data: T,
    ) : ScreenState<T>
}

suspend fun <T> ScreenState<T>.updateWhenIdle(action: suspend (T) -> T): ScreenState<T> {
    return if (this is ScreenState.Idle) ScreenState.Idle(action(data)) else this
}

suspend fun <T> StateFlow<ScreenState<T>>.updateWhenIdle(action: suspend (T) -> T): ScreenState<T> {
    return this.value.updateWhenIdle(action)
}
