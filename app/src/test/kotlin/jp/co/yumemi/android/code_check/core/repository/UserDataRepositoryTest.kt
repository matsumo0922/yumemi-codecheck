package jp.co.yumemi.android.code_check.core.repository

import io.kotest.core.spec.style.FunSpec
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import jp.co.yumemi.android.code_check.core.datastore.UserDataStore
import jp.co.yumemi.android.code_check.core.model.ThemeColorConfig
import jp.co.yumemi.android.code_check.core.model.ThemeConfig
import jp.co.yumemi.android.code_check.core.model.UserData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest

class UserDataRepositoryTest : FunSpec({

    // Mocks
    val mockDataStore = mockk<UserDataStore>()
    val userDataFlow = MutableStateFlow(UserData.default()) // Default UserData instance

    beforeTest {
        clearAllMocks()

        // Mock the userData flow
        every { mockDataStore.userData } returns userDataFlow
    }

    test("setDefault should call setDefault on data store") {
        // Arrange
        val repository = UserDataRepositoryImpl(mockDataStore)
        coEvery { mockDataStore.setDefault() } just Runs

        // Act
        runTest {
            repository.setDefault()
        }

        // Assert
        coVerify { mockDataStore.setDefault() }
    }

    test("setAgreedPrivacyPolicy should call setAgreedPrivacyPolicy on data store with correct parameters") {
        // Arrange
        val repository = UserDataRepositoryImpl(mockDataStore)
        val isAgreed = true

        coEvery { mockDataStore.setAgreedPrivacyPolicy(isAgreed) } just Runs

        // Act
        runTest {
            repository.setAgreedPrivacyPolicy(isAgreed)
        }

        // Assert
        coVerify { mockDataStore.setAgreedPrivacyPolicy(isAgreed) }
    }

    test("setAgreedTermsOfService should call setAgreedTermsOfService on data store with correct parameters") {
        // Arrange
        val repository = UserDataRepositoryImpl(mockDataStore)
        val isAgreed = true

        coEvery { mockDataStore.setAgreedTermsOfService(isAgreed) } just Runs

        // Act
        runTest {
            repository.setAgreedTermsOfService(isAgreed)
        }

        // Assert
        coVerify { mockDataStore.setAgreedTermsOfService(isAgreed) }
    }

    test("setThemeConfig should call setThemeConfig on data store with correct parameters") {
        // Arrange
        val repository = UserDataRepositoryImpl(mockDataStore)
        val themeConfig = ThemeConfig.Light

        coEvery { mockDataStore.setThemeConfig(themeConfig) } just Runs

        // Act
        runTest {
            repository.setThemeConfig(themeConfig)
        }

        // Assert
        coVerify { mockDataStore.setThemeConfig(themeConfig) }
    }

    test("setThemeColorConfig should call setThemeColorConfig on data store with correct parameters") {
        // Arrange
        val repository = UserDataRepositoryImpl(mockDataStore)
        val themeColorConfig = ThemeColorConfig.Green

        coEvery { mockDataStore.setThemeColorConfig(themeColorConfig) } just Runs

        // Act
        runTest {
            repository.setThemeColorConfig(themeColorConfig)
        }

        // Assert
        coVerify { mockDataStore.setThemeColorConfig(themeColorConfig) }
    }

    test("setUseDynamicColor should call setUseDynamicColor on data store with correct parameters") {
        // Arrange
        val repository = UserDataRepositoryImpl(mockDataStore)
        val useDynamicColor = true

        coEvery { mockDataStore.setUseDynamicColor(useDynamicColor) } just Runs

        // Act
        runTest {
            repository.setUseDynamicColor(useDynamicColor)
        }

        // Assert
        coVerify { mockDataStore.setUseDynamicColor(useDynamicColor) }
    }
})
