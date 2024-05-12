package jp.co.yumemi.android.code_check.core.model

import kotlinx.serialization.Serializable

@Serializable
data class GhRepositoryName(
    val name: String,
    val owner: String,
) {
    override fun toString(): String = "$owner/$name"
}
