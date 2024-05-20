package jp.co.yumemi.android.code_check.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
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
import jp.co.yumemi.android.code_check.core.model.ThemeColorConfig
import jp.co.yumemi.android.code_check.core.model.ThemeConfig
import jp.co.yumemi.android.code_check.core.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.json.Json

@Suppress("UnusedPrivateProperty")
class UserDataStoreTest : FunSpec({

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

    test("setThemeConfig should set the theme config value in preferences") {
        // Arrange
        val themeConfigKey = stringPreferencesKey(UserData::themeConfig.name)
        val userDataStore = UserDataStore(mockDataStore, mockFormatter)

        // Act
        userDataStore.setThemeConfig(ThemeConfig.Light)

        // Assert
        coVerify { mockPreferences[themeConfigKey] = ThemeConfig.Light.name }
    }

    test("setThemeColorConfig should set the theme color config value in preferences") {
        // Arrange
        val themeColorConfigKey = stringPreferencesKey(UserData::themeColorConfig.name)
        val userDataStore = UserDataStore(mockDataStore, mockFormatter)

        // Act
        userDataStore.setThemeColorConfig(ThemeColorConfig.Green)

        // Assert
        coVerify { mockPreferences[themeColorConfigKey] = ThemeColorConfig.Green.name }
    }

    test("setUseDynamicColor should set the boolean value in preferences") {
        // Arrange
        val isUseDynamicColorKey = booleanPreferencesKey(UserData::isUseDynamicColor.name)
        val userDataStore = UserDataStore(mockDataStore, mockFormatter)

        // Act
        userDataStore.setUseDynamicColor(true)

        // Assert
        coVerify { mockPreferences[isUseDynamicColorKey] = true }
    }

    test("setTrendSince should set the trend since value in preferences") {
        // Arrange
        val trendSinceKey = stringPreferencesKey(UserData::trendSince.name)
        val userDataStore = UserDataStore(mockDataStore, mockFormatter)

        // Act
        userDataStore.setTrendSince("daily")

        // Assert
        coVerify { mockPreferences[trendSinceKey] = "daily" }
    }

    test("setTrendLanguage should set the trend language value in preferences") {
        // Arrange
        val trendLanguageKey = stringPreferencesKey(UserData::trendLanguage.name)
        val userDataStore = UserDataStore(mockDataStore, mockFormatter)

        // Act
        userDataStore.setTrendLanguage("kotlin")

        // Assert
        coVerify { mockPreferences[trendLanguageKey] = "kotlin" }
    }
})
