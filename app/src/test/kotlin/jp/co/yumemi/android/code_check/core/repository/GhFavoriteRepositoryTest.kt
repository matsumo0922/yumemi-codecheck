package jp.co.yumemi.android.code_check.core.repository

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import jp.co.yumemi.android.code_check.core.datastore.GhCacheDataStore
import jp.co.yumemi.android.code_check.core.datastore.GhFavoriteDataStore
import jp.co.yumemi.android.code_check.core.model.GhFavorites
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.ui.previews.GhRepositoryDetailPreviewParameter
import jp.co.yumemi.android.code_check.core.ui.previews.GhUserDetailPreviewParameter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher

class GhFavoriteRepositoryImplTest : FunSpec({

    // Mocks
    val mockFavoriteDataStore = mockk<GhFavoriteDataStore>()
    val mockCacheDataStore = mockk<GhCacheDataStore>()
    val mockApiRepository = mockk<GhApiRepository>()
    val testDispatcher = StandardTestDispatcher()
    val favoriteDataFlow = MutableStateFlow(GhFavorites(emptyList(), emptyList()))

    beforeTest {
        clearAllMocks()

        // Mock the favoriteData flow
        every { mockFavoriteDataStore.favoriteData } returns favoriteDataFlow
    }

    test("clear should call clear on favorite data store") {
        // Arrange
        val repository = GhFavoriteRepositoryImpl(mockFavoriteDataStore, mockCacheDataStore, mockApiRepository, testDispatcher)
        coEvery { mockFavoriteDataStore.clear() } just Runs

        // Act
        repository.clear()
        testDispatcher.scheduler.advanceUntilIdle() // Wait for coroutine to complete

        // Assert
        coVerify { mockFavoriteDataStore.clear() }
    }

    test("addFavoriteUser should call addFavoriteUser on favorite data store with correct parameters") {
        // Arrange
        val repository = GhFavoriteRepositoryImpl(mockFavoriteDataStore, mockCacheDataStore, mockApiRepository, testDispatcher)
        val userDetail = GhUserDetailPreviewParameter.dummy

        coEvery { mockCacheDataStore.getUserCache(userDetail.name) } returns null
        coEvery { mockApiRepository.getUserDetail(userDetail.name) } returns GhUserDetailPreviewParameter.dummy
        coEvery { mockFavoriteDataStore.addFavoriteUser(userDetail.name) } just Runs

        // Act
        repository.addFavoriteUser(userDetail.name)
        testDispatcher.scheduler.advanceUntilIdle() // Wait for coroutine to complete

        // Assert
        coVerify { mockFavoriteDataStore.addFavoriteUser(userDetail.name) }
    }

    test("addFavoriteRepository should call addFavoriteRepository on favorite data store with correct parameters") {
        // Arrange
        val repository = GhFavoriteRepositoryImpl(mockFavoriteDataStore, mockCacheDataStore, mockApiRepository, testDispatcher)
        val repositoryDetail = GhRepositoryDetailPreviewParameter.dummy

        coEvery { mockCacheDataStore.getRepositoryCache(repositoryDetail.repoName) } returns null
        coEvery { mockApiRepository.getRepositoryDetail(repositoryDetail.repoName) } returns GhRepositoryDetailPreviewParameter.dummy
        coEvery { mockFavoriteDataStore.addFavoriteRepository(repositoryDetail.repoName) } just Runs

        // Act
        repository.addFavoriteRepository(repositoryDetail.repoName)
        testDispatcher.scheduler.advanceUntilIdle() // Wait for coroutine to complete

        // Assert
        coVerify { mockFavoriteDataStore.addFavoriteRepository(repositoryDetail.repoName) }
    }

    test("removeFavoriteUser should call removeFavoriteUser on favorite data store with correct parameters") {
        // Arrange
        val repository = GhFavoriteRepositoryImpl(mockFavoriteDataStore, mockCacheDataStore, mockApiRepository, testDispatcher)
        val userName = "testUser"

        coEvery { mockFavoriteDataStore.removeFavoriteUser(userName) } just Runs

        // Act
        repository.removeFavoriteUser(userName)
        testDispatcher.scheduler.advanceUntilIdle() // Wait for coroutine to complete

        // Assert
        coVerify { mockFavoriteDataStore.removeFavoriteUser(userName) }
    }

    test("removeFavoriteRepository should call removeFavoriteRepository on favorite data store with correct parameters") {
        // Arrange
        val repository = GhFavoriteRepositoryImpl(mockFavoriteDataStore, mockCacheDataStore, mockApiRepository, testDispatcher)
        val repo = GhRepositoryName("user", "repo")

        coEvery { mockFavoriteDataStore.removeFavoriteRepository(repo) } just Runs

        // Act
        repository.removeFavoriteRepository(repo)
        testDispatcher.scheduler.advanceUntilIdle() // Wait for coroutine to complete

        // Assert
        coVerify { mockFavoriteDataStore.removeFavoriteRepository(repo) }
    }

    test("isFavoriteUser should return correct result") {
        // Arrange
        val repository = GhFavoriteRepositoryImpl(mockFavoriteDataStore, mockCacheDataStore, mockApiRepository, testDispatcher)
        val userName = "testUser"
        val favoriteUsers = listOf(userName)
        favoriteDataFlow.value = GhFavorites(favoriteUsers, emptyList())

        // Act
        val result = repository.isFavoriteUser(userName)

        // Assert
        result shouldBe true
    }

    test("isFavoriteRepository should return correct result") {
        // Arrange
        val repository = GhFavoriteRepositoryImpl(mockFavoriteDataStore, mockCacheDataStore, mockApiRepository, testDispatcher)
        val repo = GhRepositoryName("user", "repo")
        val favoriteRepos = listOf(repo)
        favoriteDataFlow.value = GhFavorites(emptyList(), favoriteRepos)

        // Act
        val result = repository.isFavoriteRepository(repo)

        // Assert
        result shouldBe true
    }

    test("getFavoriteUsers should return correct favorite users") {
        // Arrange
        val repository = GhFavoriteRepositoryImpl(mockFavoriteDataStore, mockCacheDataStore, mockApiRepository, testDispatcher)
        val userName = "testUser"
        val userDetail = GhUserDetailPreviewParameter.dummy
        val favoriteUsers = listOf(userName)

        favoriteDataFlow.value = GhFavorites(favoriteUsers, emptyList())
        coEvery { mockCacheDataStore.getUserCache(userName) } returns userDetail

        // Act
        val result = repository.getFavoriteUsers()

        // Assert
        result shouldBe listOf(userDetail)
    }

    test("getFavoriteRepositories should return correct favorite repositories") {
        // Arrange
        val repository = GhFavoriteRepositoryImpl(mockFavoriteDataStore, mockCacheDataStore, mockApiRepository, testDispatcher)
        val repo = GhRepositoryName("user", "repo")
        val repoDetail = GhRepositoryDetailPreviewParameter.dummy
        val favoriteRepos = listOf(repo)

        favoriteDataFlow.value = GhFavorites(emptyList(), favoriteRepos)
        coEvery { mockCacheDataStore.getRepositoryCache(repo) } returns repoDetail

        // Act
        val result = repository.getFavoriteRepositories()

        // Assert
        result shouldBe listOf(repoDetail)
    }
})
