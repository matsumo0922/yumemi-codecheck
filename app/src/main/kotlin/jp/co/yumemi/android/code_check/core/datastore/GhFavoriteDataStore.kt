package jp.co.yumemi.android.code_check.core.datastore

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.co.yumemi.android.code_check.core.model.GhFavorites
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

class GhFavoriteDataStore(
    private val preferenceHelper: PreferenceHelper,
    private val formatter: Json,
    private val ioDispatcher: CoroutineDispatcher,
) {
    private val preference = preferenceHelper.create(PreferenceName.GH_FAVORITE)

    val favoriteData = preference.data.map { preferences ->
        val userIdsJson = preferences[stringPreferencesKey(FAVORITE_USER_KEY)]
        val repoJson = preferences[stringPreferencesKey(FAVORITE_REPO_KEY)]

        val userIds = userIdsJson?.let { formatter.decodeFromString(ListSerializer(String.serializer()), it) } ?: emptyList()
        val repos = repoJson?.let {
            formatter.decodeFromString(ListSerializer(GhRepositoryName.serializer()), it)
        } ?: emptyList()

        GhFavorites(userIds, repos)
    }

    suspend fun clear() = withContext(ioDispatcher) {
        preference.edit {
            it.clear()
        }
    }

    suspend fun addFavoriteUser(userId: String) = withContext(ioDispatcher) {
        preference.edit {
            val favoriteUserIds = favoriteData.first().userIds
            val newFavoriteUserIds = favoriteUserIds.toMutableList().apply { add(userId) }

            setFavoriteUser(it, newFavoriteUserIds)
        }
    }

    suspend fun addFavoriteRepository(repo: GhRepositoryName) = withContext(ioDispatcher) {
        preference.edit {
            val favoriteRepos = favoriteData.first().repos
            val newFavoriteRepos = favoriteRepos.toMutableList().apply { add(repo) }

            setFavoriteRepository(it, newFavoriteRepos)
        }
    }

    suspend fun removeFavoriteUser(userId: String) = withContext(ioDispatcher) {
        preference.edit {
            val favoriteUserIds = favoriteData.first().userIds
            val newFavoriteUserIds = favoriteUserIds.toMutableList().apply { remove(userId) }

            setFavoriteUser(it, newFavoriteUserIds)
        }
    }

    suspend fun removeFavoriteRepository(repo: GhRepositoryName) = withContext(ioDispatcher) {
        preference.edit {
            val favoriteRepos = favoriteData.first().repos
            val newFavoriteRepos = favoriteRepos.toMutableList().apply { remove(repo) }

            setFavoriteRepository(it, newFavoriteRepos)
        }
    }

    private fun setFavoriteRepository(preferences: MutablePreferences, repos: List<GhRepositoryName>) {
        val serializer = ListSerializer(GhRepositoryName.serializer())
        val json = formatter.encodeToString(serializer, repos)

        preferences[stringPreferencesKey(FAVORITE_REPO_KEY)] = json
    }

    private fun setFavoriteUser(preferences: MutablePreferences, userIds: List<String>) {
        val serializer = ListSerializer(String.serializer())
        val json = formatter.encodeToString(serializer, userIds)

        preferences[stringPreferencesKey(FAVORITE_USER_KEY)] = json
    }

    companion object {
        private const val FAVORITE_USER_KEY = "favorite_user"
        private const val FAVORITE_REPO_KEY = "favorite_repo"
    }
}
