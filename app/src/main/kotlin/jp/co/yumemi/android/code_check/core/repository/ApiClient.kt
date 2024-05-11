package jp.co.yumemi.android.code_check.core.repository

import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters
import io.ktor.util.InternalAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

open class ApiClient(
    private val client: HttpClient,
    private val ioDispatcher: CoroutineDispatcher,
) {
    suspend fun get(
        url: String,
        params: List<Pair<String, String>> = emptyList(),
    ): HttpResponse = withContext(ioDispatcher) {
        client.get {
            url(url.buildUrl())

            for ((key, value) in params) {
                parameter(key, value)
            }

            parameter("per_page", PER_PAGE)
        }
    }

    @OptIn(InternalAPI::class)
    suspend fun post(
        url: String,
        params: List<Pair<String, String>> = emptyList(),
    ): HttpResponse = withContext(ioDispatcher) {
        client.post {
            url(url.buildUrl())

            body = buildJsonObject {
                for ((key, value) in params) {
                    put(key, value)
                }
            }.toString()
        }
    }

    suspend fun form(
        url: String,
        params: List<Pair<String, String>> = emptyList(),
        formParams: List<Pair<String, String>> = emptyList(),
    ): HttpResponse = withContext(ioDispatcher) {
        client.submitForm(
            url = url.buildUrl(),
            formParameters = Parameters.build {
                for ((key, value) in formParams) {
                    append(key, value)
                }
            },
        ) {
            for ((key, value) in params) {
                parameter(key, value)
            }
        }
    }

    private fun String.buildUrl(): String {
        return if (this.take(4) == "http") this else "$DEFAULT_API/$this"
    }

    companion object {
        const val DEFAULT_API = "https://api.github.com"
        const val PER_PAGE = 20
    }
}
