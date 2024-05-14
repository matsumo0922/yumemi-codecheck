package jp.co.yumemi.android.code_check.core.extensions

import co.touchlab.kermit.Logger
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> suspendRunCatching(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (exception: Exception) {
    Logger.i(exception) { "Failed to evaluate a suspendRunCatchingBlock. Returning failure Result" }
    Result.failure(exception)
}
