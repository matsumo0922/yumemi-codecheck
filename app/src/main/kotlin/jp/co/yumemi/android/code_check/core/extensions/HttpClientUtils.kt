package jp.co.yumemi.android.code_check.core.extensions

import co.touchlab.kermit.Logger
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import jp.co.yumemi.android.code_check.core.model.GhPaging
import jp.co.yumemi.android.code_check.core.model.getGhPage
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
val formatter = Json {
    isLenient = true
    prettyPrint = true
    ignoreUnknownKeys = true
    coerceInputValues = true
    encodeDefaults = true
    explicitNulls = false
}

suspend inline fun <reified T> HttpResponse.parse(
    allowRange: IntRange = 200..299,
): T? {
    val requestUrl = request.url
    val isOK = this.status.value in allowRange
    val text = this.bodyAsText()

    Logger.d("[${if (isOK) "SUCCESS" else "FAILED"}] Ktor Request: $requestUrl")
    Logger.d("[RESPONSE] ${text.replace("\n", "")}")

    if (status.value == 403) throw RateLimitException()

    return if (isOK) formatter.decodeFromString(text) else null
}

suspend fun <T> HttpResponse.parsePaging(
    translate: suspend (HttpResponse) -> T,
): GhPaging<T> {
    val pagination = headers.getGhPage()

    return GhPaging(
        data = translate.invoke(this),
        pagination = pagination,
    )
}

class RateLimitException : Exception("Rate limit exceeded")
