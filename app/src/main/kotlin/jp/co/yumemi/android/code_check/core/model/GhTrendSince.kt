package jp.co.yumemi.android.code_check.core.model

import co.touchlab.kermit.Logger
import kotlinx.serialization.Serializable

@Serializable
enum class GhTrendSince(val value: String) {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    MONTHLY("Monthly"),
    ;

    companion object {
        fun from(value: String): GhTrendSince {
            return when (value.lowercase()) {
                "daily" -> DAILY
                "weekly" -> WEEKLY
                "monthly" -> MONTHLY
                else -> {
                    Logger.i { "Failed to evaluate a GhTrendSince [$value]. Returning default value." }
                    DAILY
                }
            }
        }
    }
}
