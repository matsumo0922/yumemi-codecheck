package jp.co.yumemi.android.code_check.core.repository

import io.kotest.core.spec.style.FunSpec
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import jp.co.yumemi.android.code_check.core.datastore.GhSearchHistoryDataStore
import jp.co.yumemi.android.code_check.core.model.GhSearchHistory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

class GhSearchHistoryRepositoryImplTest : FunSpec({

    // Mocks
    val mockDataStore = mockk<GhSearchHistoryDataStore>()
    val mockDispatcher = StandardTestDispatcher()
    val searchHistoriesFlow = MutableStateFlow<List<GhSearchHistory>>(emptyList())

    beforeTest {
        clearAllMocks()

        every { mockDataStore.searchHistoriesData } returns searchHistoriesFlow
    }

    test("clear should call clear on data store") {
        // Arrange
        val repository = GhSearchHistoryRepositoryImpl(mockDataStore, mockDispatcher)
        coEvery { mockDataStore.clear() } just Runs

        // Act
        repository.clear()
        mockDispatcher.scheduler.advanceUntilIdle() // Wait for coroutine to complete

        // Assert
        coVerify { mockDataStore.clear() }
    }

    test("addSearchHistory should call addSearchHistory on data store with correct parameters") {
        // Arrange
        val repository = GhSearchHistoryRepositoryImpl(mockDataStore, mockDispatcher)
        val query = "test query"
        val mockInstant = Instant.parse("2023-05-18T12:34:56.789Z")

        mockkObject(Clock.System)
        every { Clock.System.now() } returns mockInstant
        coEvery { mockDataStore.addSearchHistory(any()) } just Runs

        // Act
        repository.addSearchHistory(query)
        mockDispatcher.scheduler.advanceUntilIdle() // Wait for coroutine to complete

        // Assert
        coVerify { mockDataStore.addSearchHistory(GhSearchHistory(query, mockInstant)) }
        unmockkObject(Clock.System)
    }

    test("removeSearchHistory should call removeSearchHistory on data store with correct parameters") {
        // Arrange
        val repository = GhSearchHistoryRepositoryImpl(mockDataStore, mockDispatcher)
        val searchHistory = GhSearchHistory("test query", Instant.parse("2023-05-18T12:34:56.789Z"))

        coEvery { mockDataStore.removeSearchHistory(any()) } just Runs

        // Act
        repository.removeSearchHistory(searchHistory)
        mockDispatcher.scheduler.advanceUntilIdle() // Wait for coroutine to complete

        // Assert
        coVerify { mockDataStore.removeSearchHistory(searchHistory) }
    }
})
