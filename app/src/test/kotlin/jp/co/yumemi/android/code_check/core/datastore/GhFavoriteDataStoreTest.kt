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
import jp.co.yumemi.android.code_check.core.model.GhFavorites
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json

class GhFavoriteDataStoreTest : FunSpec({

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
        val ghFavoriteDataStore = GhFavoriteDataStore(mockDataStore, mockFormatter)

        // Act
        ghFavoriteDataStore.clear()

        // Assert
        coVerify { mockPreferences.clear() }
    }

    test("addFavoriteUser should add a favorite user to preferences") {
        // Arrange
        val userId = "user123"

        val userKey = stringPreferencesKey(GhFavoriteDataStore.FAVORITE_USER_KEY)
        val repoKey = stringPreferencesKey(GhFavoriteDataStore.FAVORITE_REPO_KEY)
        val ghFavoriteDataStore = GhFavoriteDataStore(mockDataStore, mockFormatter)

        val mockFavoriteUserIds = setOf(userId)
        val mockJson = "mockJson"

        every { mockFormatter.encodeToString(any(), mockFavoriteUserIds) } returns mockJson
        every { mockPreferences[userKey] } returns null
        every { mockPreferences[repoKey] } returns null
        coEvery { ghFavoriteDataStore.favoriteData.first() } returns GhFavorites(emptyList(), emptyList())

        // Act
        ghFavoriteDataStore.addFavoriteUser(userId)

        // Assert
        coVerify {
            mockPreferences[userKey] = mockJson
        }
    }

    test("addFavoriteRepository should add a favorite repository to preferences") {
        // Arrange
        val repo = GhRepositoryName("user", "repo")
        val userKey = stringPreferencesKey(GhFavoriteDataStore.FAVORITE_USER_KEY)
        val repoKey = stringPreferencesKey(GhFavoriteDataStore.FAVORITE_REPO_KEY)
        val ghFavoriteDataStore = GhFavoriteDataStore(mockDataStore, mockFormatter)

        val mockFavoriteRepos = setOf(repo)
        val mockJson = "mockJson"

        every { mockFormatter.encodeToString(any(), mockFavoriteRepos) } returns mockJson
        every { mockPreferences[userKey] } returns null
        every { mockPreferences[repoKey] } returns null
        coEvery { ghFavoriteDataStore.favoriteData.first() } returns GhFavorites(emptyList(), emptyList())

        // Act
        ghFavoriteDataStore.addFavoriteRepository(repo)

        // Assert
        coVerify {
            mockPreferences[repoKey] = mockJson
        }
    }

    test("removeFavoriteUser should remove a favorite user from preferences") {
        // Arrange
        val userId = "user123"
        val userKey = stringPreferencesKey(GhFavoriteDataStore.FAVORITE_USER_KEY)
        val repoKey = stringPreferencesKey(GhFavoriteDataStore.FAVORITE_REPO_KEY)
        val ghFavoriteDataStore = GhFavoriteDataStore(mockDataStore, mockFormatter)

        val mockFavoriteUserIds = emptySet<String>()
        val mockJson = "mockJson"

        every { mockFormatter.encodeToString(any(), mockFavoriteUserIds) } returns mockJson
        every { mockFormatter.decodeFromString<Set<GhRepositoryName>>(any(), any()) } returns emptySet()
        every { mockFormatter.decodeFromString<Set<String>>(any(), any()) } returns setOf(userId)
        every { mockPreferences[userKey] } returns "existingJson"
        every { mockPreferences[repoKey] } returns null
        coEvery { ghFavoriteDataStore.favoriteData.first() } returns GhFavorites(listOf(userId), emptyList())

        // Act
       ghFavoriteDataStore.removeFavoriteUser(userId)

        // Assert
        coVerify {
            mockPreferences[userKey] = mockJson
        }
    }

    test("removeFavoriteRepository should remove a favorite repository from preferences") {
        // Arrange
        val repo = GhRepositoryName("user", "repo")
        val userKey = stringPreferencesKey(GhFavoriteDataStore.FAVORITE_USER_KEY)
        val repoKey = stringPreferencesKey(GhFavoriteDataStore.FAVORITE_REPO_KEY)
        val ghFavoriteDataStore = GhFavoriteDataStore(mockDataStore, mockFormatter)

        val mockFavoriteRepos = emptySet<GhRepositoryName>()
        val mockJson = "mockJson"

        every { mockFormatter.encodeToString(any(), mockFavoriteRepos) } returns mockJson
        every { mockFormatter.decodeFromString<Set<GhRepositoryName>>(any(), any()) } returns setOf(repo)
        every { mockFormatter.decodeFromString<Set<String>>(any(), any()) } returns emptySet()
        every { mockPreferences[userKey] } returns null
        every { mockPreferences[repoKey] } returns "existingJson"
        coEvery { ghFavoriteDataStore.favoriteData.first() } returns GhFavorites(emptyList(), listOf(repo))

        // Act
        ghFavoriteDataStore.removeFavoriteRepository(repo)

        // Assert
        coVerify {
            mockPreferences[repoKey] = mockJson
        }
    }
})
