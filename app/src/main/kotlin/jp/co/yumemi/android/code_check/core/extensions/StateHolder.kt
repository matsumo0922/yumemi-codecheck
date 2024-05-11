package jp.co.yumemi.android.code_check.core.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf

@OptIn(ExperimentalStdlibApi::class)
class StateHolder : AutoCloseable {
    private val _states = mutableMapOf<String, Any?>()

    fun <T> set(key: String, value: T) {
        _states[key] = value
    }

    fun <T> getOrPut(key: String, defaultValue: () -> T): T {
        @Suppress("UNCHECKED_CAST")
        return _states.getOrPut(key) {
            defaultValue() as Any
        } as T
    }

    fun remove(key: String) {
        _states.remove(key)
    }

    operator fun <T> get(key: String): T? {
        @Suppress("UNCHECKED_CAST")
        return _states[key] as T?
    }

    override fun close() {
        for (value in _states.values) {
            if (value is AutoCloseable) {
                value.close()
            }
        }
        _states.clear()
    }

    // for testing
    internal fun contains(key: String): Boolean {
        return _states.containsKey(key)
    }
}

val LocalStateHolder = compositionLocalOf<StateHolder> {
    error("No StateHolder provided")
}

/**
 * Returns the current [StateHolder] from composition or throws an [IllegalStateException]
 * if there is no [StateHolder] in provided.
 */
val currentLocalStateHolder: StateHolder
    @Composable
    @ReadOnlyComposable
    get() = LocalStateHolder.current
