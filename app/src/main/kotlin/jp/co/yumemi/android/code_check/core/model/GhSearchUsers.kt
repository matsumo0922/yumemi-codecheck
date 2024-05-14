package jp.co.yumemi.android.code_check.core.model

import jp.co.yumemi.android.code_check.core.model.entity.SearchUsersEntity
import kotlinx.serialization.Serializable

@Serializable
data class GhSearchUsers(
    val incompleteResults: Boolean,
    val items: List<Item>,
    val totalCount: Int,
) {
    @Serializable
    data class Item(
        val avatarUrl: String,
        val eventsUrl: String,
        val followersUrl: String,
        val followingUrl: String,
        val gistsUrl: String,
        val gravatarId: String,
        val id: Int,
        val login: String,
        val nodeId: String,
        val organizationsUrl: String,
        val receivedEventsUrl: String,
        val reposUrl: String,
        val score: Float,
        val siteAdmin: Boolean,
        val starredUrl: String,
        val type: String,
        val url: String?,
    )
}

fun SearchUsersEntity.translate() = GhSearchUsers(
    incompleteResults = this.incompleteResults,
    items = this.items.map { it.translate() },
    totalCount = this.totalCount,
)

fun SearchUsersEntity.Item.translate() = GhSearchUsers.Item(
    avatarUrl = this.avatarUrl,
    eventsUrl = this.eventsUrl,
    followersUrl = this.followersUrl,
    followingUrl = this.followingUrl,
    gistsUrl = this.gistsUrl,
    gravatarId = this.gravatarId,
    id = this.id,
    login = this.login,
    nodeId = this.nodeId,
    organizationsUrl = this.organizationsUrl,
    receivedEventsUrl = this.receivedEventsUrl,
    reposUrl = this.reposUrl,
    score = this.score,
    siteAdmin = this.siteAdmin,
    starredUrl = this.starredUrl,
    type = this.type,
    url = this.url,
)
