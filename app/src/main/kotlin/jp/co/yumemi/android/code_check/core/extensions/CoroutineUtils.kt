package jp.co.yumemi.android.code_check.core.extensions

import io.github.aakira.napier.Napier
import kotlin.coroutines.cancellation.CancellationException

suspend fun <T> suspendRunCatching(block: suspend () -> T): Result<T> = try {
    Result.success(block())
} catch (cancellationException: CancellationException) {
    throw cancellationException
} catch (exception: Exception) {
    Napier.i(exception) { "Failed to evaluate a suspendRunCatchingBlock. Returning failure Result" }
    Result.failure(exception)
}
