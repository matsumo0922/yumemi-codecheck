package jp.co.yumemi.android.code_check.core.extensions

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
