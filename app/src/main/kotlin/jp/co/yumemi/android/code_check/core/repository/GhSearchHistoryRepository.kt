package jp.co.yumemi.android.code_check.core.repository

import jp.co.yumemi.android.code_check.core.datastore.GhSearchHistoryDataStore
import jp.co.yumemi.android.code_check.core.model.GhSearchHistory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

interface GhSearchHistoryRepository {
    val searchHistories: Flow<List<GhSearchHistory>>

    fun addSearchHistory(query: String)
    fun removeSearchHistory(searchHistory: GhSearchHistory)
}

class GhSearchHistoryRepositoryImpl(
    private val ghSearchHistoryDataStore: GhSearchHistoryDataStore,
    private val ioDispatcher: CoroutineDispatcher,
) : GhSearchHistoryRepository {

    private val scope = CoroutineScope(SupervisorJob() + ioDispatcher)

    override val searchHistories = ghSearchHistoryDataStore.searchHistoriesData

    override fun addSearchHistory(query: String) {
        scope.launch {
            val searchHistory = GhSearchHistory(query, Clock.System.now())
            ghSearchHistoryDataStore.addSearchHistory(searchHistory)
        }
    }

    override fun removeSearchHistory(searchHistory: GhSearchHistory) {
        scope.launch {
            ghSearchHistoryDataStore.removeSearchHistory(searchHistory)
        }
    }
}
