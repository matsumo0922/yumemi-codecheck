package jp.co.yumemi.android.code_check.core.datastore

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.co.yumemi.android.code_check.core.model.GhSearchHistory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

class GhSearchHistoryDataStore(
    private val preferenceHelper: PreferenceHelper,
    private val formatter: Json,
    private val ioDispatcher: CoroutineDispatcher,
) {
    private val preference = preferenceHelper.create(PreferenceName.GH_SEARCH_HISTORY)

    val searchHistoriesData = preference.data.map {
        val serializer = ListSerializer(GhSearchHistory.serializer())
        val json = it[stringPreferencesKey(SEARCH_HISTORY_KEY)] ?: return@map emptyList()

        formatter.decodeFromString(serializer, json)
    }

    suspend fun clear() = withContext(ioDispatcher) {
        preference.edit {
            it.clear()
        }
    }

    suspend fun addSearchHistory(searchHistory: GhSearchHistory) = withContext(ioDispatcher) {
        preference.edit {
            val searchHistories = searchHistoriesData.first().toMutableList()
            val newSearchHistories = searchHistories.apply {
                if (find { data -> data.query == searchHistory.query } != null) {
                    remove(searchHistory)
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

    suspend fun removeSearchHistory(searchHistory: GhSearchHistory) = withContext(ioDispatcher) {
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
        private const val SEARCH_HISTORY_KEY = "search_history"
        private const val MAX_HISTORY_SIZE = 5
    }
}
