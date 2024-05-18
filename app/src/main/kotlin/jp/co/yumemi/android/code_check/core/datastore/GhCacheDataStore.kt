package jp.co.yumemi.android.code_check.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhUserDetail
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.serialization.json.Json

class GhCacheDataStore(
    private val preference: DataStore<Preferences>,
    private val formatter: Json,
) {
    private val memoryUserCache = mutableMapOf<String, GhUserDetail>()
    private val memoryRepositoryCache = mutableMapOf<GhRepositoryName, GhRepositoryDetail>()

    suspend fun clear() {
        preference.edit {
            it.clear()
        }
    }

    suspend fun addUserCache(ghUserDetail: GhUserDetail) {
        memoryUserCache[ghUserDetail.name] = ghUserDetail

        preference.edit {
            val key = createUserCacheKey(ghUserDetail.name)
            val json = formatter.encodeToString(GhUserDetail.serializer(), ghUserDetail)

            it[key] = json
        }
    }

    suspend fun addRepositoryCache(ghRepositoryDetail: GhRepositoryDetail) {
        memoryRepositoryCache[ghRepositoryDetail.repoName] = ghRepositoryDetail

        preference.edit {
            val key = createRepositoryCacheKey(ghRepositoryDetail.repoName.toString())
            val json = formatter.encodeToString(GhRepositoryDetail.serializer(), ghRepositoryDetail)

            it[key] = json
        }
    }

    suspend fun getUserCache(userName: String): GhUserDetail? {
        val key = createUserCacheKey(userName)
        val json = preference.data.firstOrNull()?.get(key)

        return json?.let { formatter.decodeFromString(GhUserDetail.serializer(), it) }
    }

    suspend fun getRepositoryCache(repo: GhRepositoryName): GhRepositoryDetail? {
        val key = createRepositoryCacheKey(repo.toString())
        val json = preference.data.firstOrNull()?.get(key)

        return json?.let { formatter.decodeFromString(GhRepositoryDetail.serializer(), it) }
    }

    fun getUserMemoryCache(userName: String): GhUserDetail? {
        return memoryUserCache[userName]
    }

    fun getRepositoryMemoryCache(repo: GhRepositoryName): GhRepositoryDetail? {
        return memoryRepositoryCache[repo]
    }

    private fun createUserCacheKey(userName: String): Preferences.Key<String> {
        return stringPreferencesKey("cached-user-$userName")
    }

    private fun createRepositoryCacheKey(repositoryName: String): Preferences.Key<String> {
        return stringPreferencesKey("cached-repository-$repositoryName")
    }
}
