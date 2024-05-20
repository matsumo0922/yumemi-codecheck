package jp.co.yumemi.android.code_check.core.model

enum class GhOrder(val value: String) {
    ASC("ASC"),
    DESC("DESC"),
    ;

    companion object {
        fun fromValue(value: String): GhOrder? {
            return when (value.lowercase()) {
                "asc" -> ASC
                "desc" -> DESC
                else -> null
            }
        }
    }
}
