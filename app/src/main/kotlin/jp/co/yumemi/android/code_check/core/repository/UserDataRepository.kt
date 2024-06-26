package jp.co.yumemi.android.code_check.core.repository

import jp.co.yumemi.android.code_check.core.datastore.UserDataStore
import jp.co.yumemi.android.code_check.core.model.GhLanguage
import jp.co.yumemi.android.code_check.core.model.GhOrder
import jp.co.yumemi.android.code_check.core.model.GhRepositorySort
import jp.co.yumemi.android.code_check.core.model.GhTrendSince
import jp.co.yumemi.android.code_check.core.model.ThemeColorConfig
import jp.co.yumemi.android.code_check.core.model.ThemeConfig
import jp.co.yumemi.android.code_check.core.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>

    suspend fun setDefault()
    suspend fun setAgreedPrivacyPolicy(isAgreed: Boolean)
    suspend fun setAgreedTermsOfService(isAgreed: Boolean)
    suspend fun setThemeConfig(themeConfig: ThemeConfig)
    suspend fun setThemeColorConfig(themeColorConfig: ThemeColorConfig)
    suspend fun setUseDynamicColor(useDynamicColor: Boolean)
    suspend fun setTrendLanguage(language: GhLanguage?)
    suspend fun setTrendSince(since: GhTrendSince)
    suspend fun setSearchOrder(order: GhOrder?)
    suspend fun setSearchSort(sort: GhRepositorySort?)
}

class UserDataRepositoryImpl(
    private val userDataStore: UserDataStore,
) : UserDataRepository {

    override val userData: Flow<UserData> = userDataStore.userData

    override suspend fun setDefault() {
        userDataStore.setDefault()
    }

    override suspend fun setAgreedPrivacyPolicy(isAgreed: Boolean) {
        userDataStore.setAgreedPrivacyPolicy(isAgreed)
    }

    override suspend fun setAgreedTermsOfService(isAgreed: Boolean) {
        userDataStore.setAgreedTermsOfService(isAgreed)
    }

    override suspend fun setThemeConfig(themeConfig: ThemeConfig) {
        userDataStore.setThemeConfig(themeConfig)
    }

    override suspend fun setThemeColorConfig(themeColorConfig: ThemeColorConfig) {
        userDataStore.setThemeColorConfig(themeColorConfig)
    }

    override suspend fun setUseDynamicColor(useDynamicColor: Boolean) {
        userDataStore.setUseDynamicColor(useDynamicColor)
    }

    override suspend fun setTrendLanguage(language: GhLanguage?) {
        userDataStore.setTrendLanguage(language?.title.orEmpty())
    }

    override suspend fun setTrendSince(since: GhTrendSince) {
        userDataStore.setTrendSince(since.value)
    }

    override suspend fun setSearchOrder(order: GhOrder?) {
        userDataStore.setSearchOrder(order?.value.orEmpty())
    }

    override suspend fun setSearchSort(sort: GhRepositorySort?) {
        userDataStore.setSearchSort(sort?.value.orEmpty())
    }
}
