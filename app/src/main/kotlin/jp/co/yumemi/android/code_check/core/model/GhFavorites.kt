package jp.co.yumemi.android.code_check.core.model

import kotlinx.serialization.Serializable

@Serializable
data class GhFavorites(
    val userIds: List<String>,
    val repos: List<GhRepositoryName>,
) {
    companion object {
        fun default() = GhFavorites(emptyList(), emptyList())
    }
}
