package jp.co.yumemi.android.code_check.core.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters
import io.ktor.util.InternalAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject

open class ApiClient(
    private val client: HttpClient,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun get(
        url: String,
        params: Map<String, String?> = emptyMap(),
        headers: Map<String, String?> = mapOf("Accept" to "application/vnd.github.mercy-preview+json"),
    ): HttpResponse = withContext(ioDispatcher) {
        client.get {
            url(url.buildUrl())

            for ((key, value) in headers) {
                value?.let { header(key, it) }
            }

            for ((key, value) in params) {
                value?.let { parameter(key, it) }
            }
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun post(
        url: String,
        params: Map<String, String?> = emptyMap(),
    ): HttpResponse = withContext(ioDispatcher) {
        client.post {
            url(url.buildUrl())

            body = buildJsonObject {
                for ((key, value) in params) {
                    value?.let { parameter(key, it) }
                }
            }.toString()
        }
    }

    suspend fun form(
        url: String,
        params: Map<String, String?> = emptyMap(),
        formParams: Map<String, String?> = emptyMap(),
    ): HttpResponse = withContext(ioDispatcher) {
        client.submitForm(
            url = url.buildUrl(),
            formParameters = Parameters.build {
                for ((key, value) in formParams) {
                    value?.let { append(key, it) }
                }
            },
        ) {
            for ((key, value) in params) {
                value?.let { parameter(key, it) }
            }
        }
    }

    private fun String.buildUrl(): String {
        return if (this.take(4) == "http") this else "$DEFAULT_API/$this"
    }

    companion object {
        const val DEFAULT_API = "https://api.github.com"
        const val PER_PAGE = 30
    }
}
