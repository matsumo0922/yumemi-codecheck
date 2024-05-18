package jp.co.yumemi.android.code_check.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.kotest.core.spec.style.FunSpec
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coInvoke
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import jp.co.yumemi.android.code_check.core.model.GhSearchHistory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

class GhSearchHistoryDataStoreTest : FunSpec({

    // Mocks
    val mockFormatter = mockk<Json>()

    // Mock DataStore
    val mockEdit = mockkStatic(DataStore<Preferences>::edit)
    val mockPreferences: MutablePreferences = mockk(relaxUnitFun = true)
    val mockPreferenceData = MutableStateFlow<Preferences>(mockPreferences)
    val mockDataStore: DataStore<Preferences> = mockk()

    beforeTest {
        clearAllMocks()

        every { mockPreferences.toMutablePreferences() } returns mockPreferences
        every { mockDataStore.data } returns mockPreferenceData

        coEvery { mockDataStore.edit(captureLambda()) } coAnswers {
            lambda<suspend (MutablePreferences) -> Unit>().coInvoke(mockPreferences)
            mockPreferences
        }

        coEvery { mockDataStore.updateData(captureLambda()) } coAnswers {
            lambda<suspend (MutablePreferences) -> Unit>().coInvoke(mockPreferences)
            mockPreferences
        }
    }

    test("clear should clear the preferences") {
        // Arrange
        val ghSearchHistoryDataStore = GhSearchHistoryDataStore(mockDataStore, mockFormatter)

        // Act
        ghSearchHistoryDataStore.clear()

        // Assert
        coVerify { mockPreferences.clear() }
    }

    test("addSearchHistory should add a search history to preferences") {
        // Arrange
        val searchHistory = GhSearchHistory("query", Instant.parse("2023-05-18T12:34:56.789Z"))
        val searchHistoryKey = stringPreferencesKey(GhSearchHistoryDataStore.SEARCH_HISTORY_KEY)
        val ghSearchHistoryDataStore = GhSearchHistoryDataStore(mockDataStore, mockFormatter)

        val mockSearchHistories = listOf(searchHistory)
        val mockJson = "mockJson"

        every { mockFormatter.encodeToString(any(), mockSearchHistories) } returns mockJson
        every { mockPreferences[searchHistoryKey] } returns null
        coEvery { ghSearchHistoryDataStore.searchHistoriesData.first() } returns emptyList()

        // Act
        ghSearchHistoryDataStore.addSearchHistory(searchHistory)

        // Assert
        coVerify { mockPreferences[searchHistoryKey] = mockJson }
    }

    test("removeSearchHistory should remove a search history from preferences") {
        // Arrange
        val searchHistory = GhSearchHistory("query", Instant.parse("2023-05-18T12:34:56.789Z"))
        val searchHistoryKey = stringPreferencesKey(GhSearchHistoryDataStore.SEARCH_HISTORY_KEY)
        val ghSearchHistoryDataStore = GhSearchHistoryDataStore(mockDataStore, mockFormatter)

        val mockSearchHistories = listOf(searchHistory)
        val mockJson = "mockJson"

        every { mockFormatter.encodeToString(any(), emptyList<GhSearchHistory>()) } returns mockJson
        every { mockFormatter.decodeFromString<List<GhSearchHistory>>(any(), any()) } returns mockSearchHistories
        every { mockPreferences[searchHistoryKey] } returns "existingJson"
        coEvery { ghSearchHistoryDataStore.searchHistoriesData.first() } returns mockSearchHistories

        // Act
        ghSearchHistoryDataStore.removeSearchHistory(searchHistory)

        // Assert
        coVerify { mockPreferences[searchHistoryKey] = mockJson }
    }
})
