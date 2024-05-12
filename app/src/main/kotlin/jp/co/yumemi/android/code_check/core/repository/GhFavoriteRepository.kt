package jp.co.yumemi.android.code_check.core.repository

import jp.co.yumemi.android.code_check.core.datastore.GhCacheDataStore
import jp.co.yumemi.android.code_check.core.datastore.GhFavoriteDataStore
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import jp.co.yumemi.android.code_check.core.model.GhRepositoryName
import jp.co.yumemi.android.code_check.core.model.GhUserDetail
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

interface GhFavoriteRepository {

    fun setFavoriteUser(userName: String)
    fun setFavoriteRepository(repo: GhRepositoryName)

    fun removeFavoriteUser(userName: String)
    fun removeFavoriteRepository(repo: GhRepositoryName)

    suspend fun getFavoriteUsers(): List<GhUserDetail>
    suspend fun getFavoriteRepositories(): List<GhRepositoryDetail>
}

class GhFavoriteRepositoryImpl(
    private val ghFavoriteDataStore: GhFavoriteDataStore,
    private val ghCacheDataStore: GhCacheDataStore,
    private val ghApiRepository: GhApiRepository,
    private val ioDispatcher: CoroutineDispatcher,
) : GhFavoriteRepository {

    private val scope = CoroutineScope(SupervisorJob() + ioDispatcher)

    override fun setFavoriteUser(userName: String) {
        scope.launch {
            val cache = ghCacheDataStore.getUserCache(userName)
            val userDetail = cache ?: ghApiRepository.getUserDetail(userName)

            ghFavoriteDataStore.addFavoriteUser(userDetail.name)
        }
    }

    override fun setFavoriteRepository(repo: GhRepositoryName) {
        scope.launch {
            val cache = ghCacheDataStore.getRepositoryCache(repo)
            val repositoryDetail = cache ?: ghApiRepository.getRepositoryDetail(repo)

            ghFavoriteDataStore.addFavoriteRepository(repositoryDetail.repo)
        }
    }

    override fun removeFavoriteUser(userName: String) {
        scope.launch {
            ghFavoriteDataStore.removeFavoriteUser(userName)
        }
    }

    override fun removeFavoriteRepository(repo: GhRepositoryName) {
        scope.launch {
            ghFavoriteDataStore.removeFavoriteRepository(repo)
        }
    }

    override suspend fun getFavoriteUsers(): List<GhUserDetail> {
        return ghFavoriteDataStore.favoriteData.first().userIds.map {
            ghCacheDataStore.getUserCache(it) ?: ghApiRepository.getUserDetail(it)
        }
    }

    override suspend fun getFavoriteRepositories(): List<GhRepositoryDetail> {
        return ghFavoriteDataStore.favoriteData.first().repos.map {
            ghCacheDataStore.getRepositoryCache(it) ?: ghApiRepository.getRepositoryDetail(it)
        }
    }
}
