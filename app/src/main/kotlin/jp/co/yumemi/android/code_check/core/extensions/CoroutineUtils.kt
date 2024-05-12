package jp.co.yumemi.android.code_check.core.extensions

import android.util.Log
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> suspendRunCatching(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (exception: Exception) {
    Log.i("suspendRunCatching", "Failed to evaluate a suspendRunCatchingBlock. Returning failure Result")
    Log.i("suspendRunCatching", exception.toString())
    Result.failure(exception)
}
