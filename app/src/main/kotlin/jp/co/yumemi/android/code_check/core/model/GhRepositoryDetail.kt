package jp.co.yumemi.android.code_check.core.model

import jp.co.yumemi.android.code_check.core.model.entity.RepositoryDetailEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable

@Serializable
data class GhRepositoryDetail(
    val isArchived: Boolean,
    val createdAt: Instant,
    val defaultBranch: String,
    val description: String?,
    val isDisabled: Boolean,
    val isFork: Boolean,
    val forks: Int,
    val forksCount: Int,
    val fullName: String,
    val hasDiscussions: Boolean,
    val hasDownloads: Boolean,
    val hasIssues: Boolean,
    val hasPages: Boolean,
    val hasProjects: Boolean,
    val hasWiki: Boolean,
    val homepage: String?,
    val id: Int,
    val isTemplate: Boolean,
    val name: String,
    val networkCount: Int,
    val nodeId: String,
    val language: String?,
    val openIssues: Int,
    val openIssuesCount: Int,
    val owner: Owner,
    val parent: GhRepositoryDetail?,
    val isPrivate: Boolean,
    val pushedAt: Instant,
    val size: Int,
    val source: GhRepositoryDetail?,
    val stargazersCount: Int,
    val subscribersCount: Int,
    val tempCloneToken: String?,
    val templateRepository: GhRepositoryDetail?,
    val topics: List<String>,
    val updatedAt: Instant,
    val visibility: String,
    val watchers: Int,
    val watchersCount: Int,
) {
    val repoName: GhRepositoryName = GhRepositoryName(
        owner = this.owner.login,
        name = this.name,
    )

    @Serializable
    data class Owner(
        val avatarUrl: String,
        val gravatarId: String,
        val id: Int,
        val login: String,
        val nodeId: String,
        val type: String,
        val url: String?,
    )
}

fun RepositoryDetailEntity.translate(): GhRepositoryDetail {
    return GhRepositoryDetail(
        isArchived = this.archived,
        createdAt = this.createdAt.toInstant(),
        defaultBranch = this.defaultBranch,
        description = this.description,
        isDisabled = this.disabled,
        isFork = this.fork,
        forks = this.forks,
        forksCount = this.forksCount,
        fullName = this.fullName,
        hasDiscussions = this.hasDiscussions,
        hasDownloads = this.hasDownloads,
        hasIssues = this.hasIssues,
        hasPages = this.hasPages,
        hasProjects = this.hasProjects,
        hasWiki = this.hasWiki,
        homepage = this.homepage,
        id = this.id,
        isTemplate = this.isTemplate,
        name = this.name,
        networkCount = this.networkCount,
        nodeId = this.nodeId,
        language = this.languages,
        openIssues = this.openIssues,
        openIssuesCount = this.openIssuesCount,
        owner = this.owner.translate(),
        parent = this.parent?.translate(),
        isPrivate = this.private,
        pushedAt = this.pushedAt.toInstant(),
        size = this.size,
        source = this.source?.translate(),
        stargazersCount = this.stargazersCount,
        subscribersCount = this.subscribersCount,
        tempCloneToken = this.tempCloneToken,
        templateRepository = this.templateRepository?.translate(),
        topics = this.topics,
        updatedAt = this.updatedAt.toInstant(),
        visibility = this.visibility,
        watchers = this.watchers,
        watchersCount = this.watchersCount,
    )
}

fun RepositoryDetailEntity.Owner.translate() = GhRepositoryDetail.Owner(
    avatarUrl = this.avatarUrl,
    gravatarId = this.gravatarId,
    id = this.id,
    login = this.login,
    nodeId = this.nodeId,
    type = this.type,
    url = this.url,
)
