package jp.co.yumemi.android.code_check.core.model

import co.touchlab.kermit.Logger

enum class GhOrder(val value: String) {
    ASC("ASC"),
    DESC("DESC"),
    ;

    companion object {
        fun fromValue(value: String): GhOrder {
            return when (value) {
                "ASC" -> ASC
                "DESC" -> DESC
                else -> {
                    Logger.i { "Unknown value for GhOrder: $value" }
                    ASC
                }
            }
        }
    }
}
