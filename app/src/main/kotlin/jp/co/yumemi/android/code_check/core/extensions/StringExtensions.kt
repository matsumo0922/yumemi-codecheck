package jp.co.yumemi.android.code_check.core.extensions

import androidx.compose.ui.graphics.Color
import com.fleeksoft.ksoup.Ksoup

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}

fun String.indexOfWordStartsWith(prefix: String): Int {
    if (prefix.split(" ").size > 1) {
        return indexOf(string = prefix, ignoreCase = true)
    }

    val searchableTextWords = split(" ")
    var index = -1

    searchableTextWords.forEach { word ->
        val startWith = word.startsWith(prefix = prefix, ignoreCase = true)
        if (startWith) {
            index = indexOf(string = word, ignoreCase = true)
            return@forEach
        }
    }
    return index
}

fun String.isAnyWordStartsWith(prefix: String): Boolean = indexOfWordStartsWith(prefix) != -1

fun convertRelativeUrlsToAbsolute(html: String, baseUrl: String): String {
    val document = Ksoup.parse(html, baseUrl)
    val urlAttributes = mapOf(
        "img" to "src",
        "link" to "href",
        "script" to "src",
        "video" to "src",
        "audio" to "src",
        "source" to "src",  // for <video> and <audio> tags
        "iframe" to "src",
        "embed" to "src",
        "object" to "data",
        "use" to "xlink:href"  // for SVG use tags
    )

    urlAttributes.forEach { (tag, attribute) ->
        document.select("$tag[$attribute]").forEach { element ->
            val absUrl = element.absUrl(attribute)
            if (absUrl.isNotEmpty()) {
                element.attr(attribute, absUrl)
            }
        }
    }

    return document.toString()
}
