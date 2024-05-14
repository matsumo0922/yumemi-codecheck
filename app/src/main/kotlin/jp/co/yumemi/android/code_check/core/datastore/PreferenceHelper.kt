package jp.co.yumemi.android.code_check.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import co.touchlab.kermit.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonUnquotedLiteral
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import okio.Path.Companion.toPath

interface PreferenceHelper {
    fun create(name: String): DataStore<Preferences>
    fun delete(name: String)
}

class PreferenceHelperImpl(
    private val context: Context,
    private val ioDispatcher: CoroutineDispatcher,
) : PreferenceHelper {
    override fun create(name: String): DataStore<Preferences> {
        val file = context.filesDir.resolve("$name.preferences_pb")

        return PreferenceDataStoreFactory.createWithPath(
            corruptionHandler = null,
            migrations = emptyList(),
            scope = CoroutineScope(ioDispatcher),
            produceFile = { file.absolutePath.toPath() },
        )
    }

    override fun delete(name: String) {
        // not implemented yet
    }
}

@OptIn(ExperimentalSerializationApi::class)
fun <T> Preferences.deserialize(
    formatter: Json,
    serializer: KSerializer<T>,
    defaultValue: T,
): T {
    return try {
        val map = this.asMap().map { it.key.name to JsonUnquotedLiteral(it.value.toString()) }.toMap()
        val defaultData = formatter.encodeToJsonElement(serializer, defaultValue)
        val preferenceData = JsonObject(map)

        val data = buildJsonObject {
            for (value in defaultData.jsonObject) {
                put(value.key, value.value)
            }

            for (value in preferenceData.jsonObject) {
                put(value.key, value.value)
            }
        }

        formatter.decodeFromJsonElement(serializer, data)
    } catch (e: Exception) {
        Logger.e("Failed to deserialize.", e)
        defaultValue
    }
}
