package jp.co.yumemi.android.code_check.core.datastore

import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import jp.co.yumemi.android.code_check.core.model.GhFavorites
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import kotlinx.coroutines.CoroutineDispatcher
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
    private val loginPreference = preferenceHelper.create(PreferenceName.GH_FAVORITE)

    val loginData = loginPreference.data.map {
        it.deserialize(formatter, GhFavorites.serializer(), GhFavorites.default())
    }

    suspend fun clear() {
        GhFavorites.default().also { data ->
            setFavoriteUser(data.userIds)
            setFavoriteRepository(data.repos)
        }
    }

    suspend fun setFavoriteUser(userIds: List<String>) = withContext(ioDispatcher) {
        loginPreference.edit {
            val serializer = ListSerializer(String.serializer())
            val json = formatter.encodeToString(serializer, userIds)

            it[stringPreferencesKey(FAVORITE_USER_KEY)] = json
        }
    }

    suspend fun setFavoriteRepository(repos: List<GhRepositoryName>) = withContext(ioDispatcher) {
        loginPreference.edit {
            val serializer = ListSerializer(GhRepositoryName.serializer())
            val json = formatter.encodeToString(serializer, repos)

            it[stringPreferencesKey(FAVORITE_REPO_KEY)] = json
        }
    }

    companion object {
        private const val FAVORITE_USER_KEY = "favorite_user"
        private const val FAVORITE_REPO_KEY = "favorite_repo"
    }
}