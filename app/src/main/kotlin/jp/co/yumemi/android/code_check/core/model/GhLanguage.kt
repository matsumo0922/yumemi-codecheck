@file:UseSerializers(ColorSerializer::class)

package jp.co.yumemi.android.code_check.core.model

import androidx.compose.ui.graphics.Color
import jp.co.yumemi.android.code_check.core.common.extensions.toColor
import jp.co.yumemi.android.code_check.core.common.serializer.ColorSerializer
import jp.co.yumemi.android.code_check.core.model.entity.GhLanguageEntity
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers

@Serializable
data class GhLanguage(
    val color: Color,
    val name: String,
    val title: String,
    val url: String,
)

fun List<GhLanguageEntity>.translate(): List<GhLanguage> {
    return map {
        GhLanguage(
            color = it.color.toColor(),
            name = it.name,
            title = it.title,
            url = it.url,
        )
    }
}
