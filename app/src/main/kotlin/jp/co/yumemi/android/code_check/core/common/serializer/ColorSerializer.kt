package jp.co.yumemi.android.code_check.core.common.serializer

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt
import jp.co.yumemi.android.code_check.core.common.extensions.colorToHexString
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object ColorSerializer : KSerializer<Color> {
    override fun serialize(encoder: Encoder, value: Color) =
        encoder.encodeString(colorToHexString(value))

    override fun deserialize(decoder: Decoder) =
        Color(decoder.decodeString().toColorInt())

    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor(
        serialName = "Color",
        kind = PrimitiveKind.STRING,
    )
}
