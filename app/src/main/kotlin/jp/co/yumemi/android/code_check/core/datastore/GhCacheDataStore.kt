package jp.co.yumemi.android.code_check.core.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.model.GhUserDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class GhCacheDataStore(
    private val preferenceHelper: PreferenceHelper,
    private val formatter: Json,
    private val ioDispatcher: CoroutineDispatcher,
) {
    private val loginPreference = preferenceHelper.create(PreferenceName.GH_CACHE)

    suspend fun setUserCache(ghUserDetail: GhUserDetail) = withContext(ioDispatcher) {
        loginPreference.edit {
            val key = createUserCacheKey(ghUserDetail.name)
            val json = formatter.encodeToString(GhUserDetail.serializer(), ghUserDetail)

            it[key] = json
        }
    }

    suspend fun setRepositoryCache(ghRepositoryDetail: GhRepositoryDetail) = withContext(ioDispatcher) {
        loginPreference.edit {
            val key = createRepositoryCacheKey(ghRepositoryDetail.name)
            val json = formatter.encodeToString(GhRepositoryDetail.serializer(), ghRepositoryDetail)

            it[key] = json
        }
    }

    suspend fun getUserCache(userName: String): GhUserDetail? {
        val key = createUserCacheKey(userName)
        val json = loginPreference.data.firstOrNull()?.get(key)

        return json?.let { formatter.decodeFromString(GhUserDetail.serializer(), it) }
    }

    suspend fun getRepositoryCache(repositoryName: String): GhRepositoryDetail? {
        val key = createRepositoryCacheKey(repositoryName)
        val json = loginPreference.data.firstOrNull()?.get(key)

        return json?.let { formatter.decodeFromString(GhRepositoryDetail.serializer(), it) }
    }

    private fun createUserCacheKey(userName: String): Preferences.Key<String> {
        return stringPreferencesKey("cached-user-$userName")
    }

    private fun createRepositoryCacheKey(repositoryName: String): Preferences.Key<String> {
        return stringPreferencesKey("cached-repository-$repositoryName")
    }
}