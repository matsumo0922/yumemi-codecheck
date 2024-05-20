package jp.co.yumemi.android.code_check.core.model.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GhLanguageEntity(
    @SerialName("color")
    val color: String,
    @SerialName("name")
    val name: String,
    @SerialName("title")
    val title: String,
    @SerialName("url")
    val url: String,
)
