package jp.co.yumemi.android.code_check.core.model

import jp.co.yumemi.android.code_check.core.model.entity.UserDetailEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable

@Serializable
data class GhUserDetail(
    val avatarUrl: String,
    val bio: String,
    val blog: String,
    val company: String,
    val createdAt: Instant,
    val email: String,
    val eventsUrl: String,
    val followers: Int,
    val followersUrl: String,
    val following: Int,
    val followingUrl: String,
    val gistsUrl: String,
    val gravatarId: String,
    val hireable: Boolean,
    val id: Int,
    val location: String,
    val login: String,
    val name: String,
    val nodeId: String,
    val organizationsUrl: String,
    val publicGists: Int,
    val publicRepos: Int,
    val receivedEventsUrl: String,
    val reposUrl: String,
    val siteAdmin: Boolean,
    val starredUrl: String,
    val twitterUsername: String,
    val type: String,
    val updatedAt: Instant,
    val url: String?,
)

// Extension function to translate DTO to Model
fun UserDetailEntity.translate() = GhUserDetail(
    avatarUrl = this.avatarUrl,
    bio = this.bio,
    blog = this.blog,
    company = this.company,
    createdAt = this.createdAt.toInstant(),
    email = this.email,
    eventsUrl = this.eventsUrl,
    followers = this.followers,
    followersUrl = this.followersUrl,
    following = this.following,
    followingUrl = this.followingUrl,
    gistsUrl = this.gistsUrl,
    gravatarId = this.gravatarId,
    hireable = this.hireable,
    id = this.id,
    location = this.location,
    login = this.login,
    name = this.name,
    nodeId = this.nodeId,
    organizationsUrl = this.organizationsUrl,
    publicGists = this.publicGists,
    publicRepos = this.publicRepos,
    receivedEventsUrl = this.receivedEventsUrl,
    reposUrl = this.reposUrl,
    siteAdmin = this.siteAdmin,
    starredUrl = this.starredUrl,
    twitterUsername = this.twitterUsername,
    type = this.type,
    updatedAt = this.updatedAt.toInstant(),
    url = this.url,
)
