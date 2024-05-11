package jp.co.yumemi.android.code_check.core.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.co.yumemi.android.code_check.core.model.ThemeColorConfig
import jp.co.yumemi.android.code_check.core.model.ThemeConfig
import jp.co.yumemi.android.code_check.core.model.UserData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class UserDataStore(
    private val preferenceHelper: PreferenceHelper,
    private val formatter: Json,
    private val ioDispatcher: CoroutineDispatcher,
) {
    private val userPreference = preferenceHelper.create(PreferenceName.USER_DATA)

    val userData = userPreference.data.map {
        it.deserialize(formatter, UserData.serializer(), UserData.default())
    }

    suspend fun setDefault() = withContext(ioDispatcher) {
        UserData.default().also { data ->
            setAgreedPrivacyPolicy(data.isAgreedPrivacyPolicy)
            setAgreedTermsOfService(data.isAgreedTermsOfService)
            setThemeConfig(data.themeConfig)
            setThemeColorConfig(data.themeColorConfig)
            setUseDynamicColor(data.isUseDynamicColor)
        }
    }

    suspend fun setAgreedPrivacyPolicy(isAgreed: Boolean) = withContext(ioDispatcher) {
        userPreference.edit {
            it[booleanPreferencesKey(UserData::isAgreedPrivacyPolicy.name)] = isAgreed
        }
    }

    suspend fun setAgreedTermsOfService(isAgreed: Boolean) = withContext(ioDispatcher) {
        userPreference.edit {
            it[booleanPreferencesKey(UserData::isAgreedTermsOfService.name)] = isAgreed
        }
    }

    suspend fun setThemeConfig(themeConfig: ThemeConfig) = withContext(ioDispatcher) {
        userPreference.edit {
            it[stringPreferencesKey(UserData::themeConfig.name)] = themeConfig.name
        }
    }

    suspend fun setThemeColorConfig(themeColorConfig: ThemeColorConfig) = withContext(ioDispatcher) {
        userPreference.edit {
            it[stringPreferencesKey(UserData::themeColorConfig.name)] = themeColorConfig.name
        }
    }

    suspend fun setUseDynamicColor(useDynamicColor: Boolean) = withContext(ioDispatcher) {
        userPreference.edit {
            it[booleanPreferencesKey(UserData::isUseDynamicColor.name)] = useDynamicColor
        }
    }
}