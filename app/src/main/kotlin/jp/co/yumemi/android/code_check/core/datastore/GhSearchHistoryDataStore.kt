package jp.co.yumemi.android.code_check.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.co.yumemi.android.code_check.core.model.GhSearchHistory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class GhSearchHistoryDataStore(
    private val preference: DataStore<Preferences>,
    private val formatter: Json,
) {
    val searchHistoriesData = preference.data.map {
        val serializer = ListSerializer(GhSearchHistory.serializer())
        val json = it[stringPreferencesKey(SEARCH_HISTORY_KEY)] ?: return@map emptyList()

        formatter.decodeFromString(serializer, json)
    }

    suspend fun clear() {
        preference.edit {
            it.clear()
        }
    }

    suspend fun addSearchHistory(searchHistory: GhSearchHistory) {
        preference.edit {
            val searchHistories = searchHistoriesData.first().toMutableList()
            val newSearchHistories = searchHistories.apply {
                find { data -> data.query == searchHistory.query }?.let { data ->
                    remove(data)
                }

                add(0, searchHistory)

                if (size > MAX_HISTORY_SIZE) {
                    removeLast()
                }
            }

            val serializer = ListSerializer(GhSearchHistory.serializer())
            val json = formatter.encodeToString(serializer, newSearchHistories)

            it[stringPreferencesKey(SEARCH_HISTORY_KEY)] = json
        }
    }

    suspend fun removeSearchHistory(searchHistory: GhSearchHistory) {
        preference.edit {
            val searchHistories = searchHistoriesData.first().toMutableList()
            val newSearchHistories = searchHistories.apply {
                remove(searchHistory)
            }

            val serializer = ListSerializer(GhSearchHistory.serializer())
            val json = formatter.encodeToString(serializer, newSearchHistories)

            it[stringPreferencesKey(SEARCH_HISTORY_KEY)] = json
        }
    }

    companion object {
        const val SEARCH_HISTORY_KEY = "search_history"
        const val MAX_HISTORY_SIZE = 5
    }
}
