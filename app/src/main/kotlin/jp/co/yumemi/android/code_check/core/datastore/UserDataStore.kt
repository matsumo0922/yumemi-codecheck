package jp.co.yumemi.android.code_check.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.co.yumemi.android.code_check.core.model.ThemeColorConfig
import jp.co.yumemi.android.code_check.core.model.ThemeConfig
import jp.co.yumemi.android.code_check.core.model.UserData
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

class UserDataStore(
    private val preference: DataStore<Preferences>,
    private val formatter: Json,
) {
    val userData = preference.data.map {
        it.deserialize(formatter, UserData.serializer(), UserData.default())
    }

    suspend fun setDefault() {
        UserData.default().also { data ->
            setAgreedPrivacyPolicy(data.isAgreedPrivacyPolicy)
            setAgreedTermsOfService(data.isAgreedTermsOfService)
            setThemeConfig(data.themeConfig)
            setThemeColorConfig(data.themeColorConfig)
            setUseDynamicColor(data.isUseDynamicColor)
            setTrendSince(data.trendSince)
            setTrendLanguage(data.trendLanguage)
        }
    }

    suspend fun setAgreedPrivacyPolicy(isAgreed: Boolean) {
        preference.edit {
            it[booleanPreferencesKey(UserData::isAgreedPrivacyPolicy.name)] = isAgreed
        }
    }

    suspend fun setAgreedTermsOfService(isAgreed: Boolean) {
        preference.edit {
            it[booleanPreferencesKey(UserData::isAgreedTermsOfService.name)] = isAgreed
        }
    }

    suspend fun setThemeConfig(themeConfig: ThemeConfig) {
        preference.edit {
            it[stringPreferencesKey(UserData::themeConfig.name)] = themeConfig.name
        }
    }

    suspend fun setThemeColorConfig(themeColorConfig: ThemeColorConfig) {
        preference.edit {
            it[stringPreferencesKey(UserData::themeColorConfig.name)] = themeColorConfig.name
        }
    }

    suspend fun setUseDynamicColor(useDynamicColor: Boolean) {
        preference.edit {
            it[booleanPreferencesKey(UserData::isUseDynamicColor.name)] = useDynamicColor
        }
    }

    suspend fun setTrendSince(trendSince: String) {
        preference.edit {
            it[stringPreferencesKey(UserData::trendSince.name)] = trendSince
        }
    }

    suspend fun setTrendLanguage(trendLanguage: String) {
        preference.edit {
            it[stringPreferencesKey(UserData::trendLanguage.name)] = trendLanguage
        }
    }
}
