package jp.co.yumemi.android.code_check.core.model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class GhSearchHistory(
    val query: String,
    val searchAt: Instant,
)
