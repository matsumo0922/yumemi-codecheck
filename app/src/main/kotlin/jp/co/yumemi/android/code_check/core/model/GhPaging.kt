package jp.co.yumemi.android.code_check.core.model

import io.ktor.http.Headers
import io.ktor.http.Url

data class GhPage(
    val next: Int?,
    val previous: Int?,
    val first: Int?,
    val last: Int?,
)

data class GhPaging <out T> (
    val data: T,
    val pagination: GhPage,
)

fun Headers.getGhPage(): GhPage {
    var pagination = GhPage(null, null, null, null)

    val links = this["Link"] ?: return pagination
    val items = links.split(",").map { it.trim() }

    for (item in items) {
        val parts = item.split(";")
        val url = parts[0].trim().removePrefix("<").removeSuffix(">")
        val rel = parts[1].trim().removePrefix("rel=\"").removeSuffix("\"")
        val page = Url(url).parameters["page"]?.toInt()

        pagination = when (rel) {
            "next" -> pagination.copy(next = page)
            "previous" -> pagination.copy(previous = page)
            "first" -> pagination.copy(first = page)
            "last" -> pagination.copy(last = page)
            else -> pagination
        }
    }

    return pagination
}