package jp.co.yumemi.android.code_check.core.model

import jp.co.yumemi.android.code_check.core.model.entity.SearchRepositoriesEntity
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class GhSearchRepositories(
    val isIncompleteResults: Boolean,
    val items: List<Item>,
    val totalCount: Int,
) {
    @Serializable
    data class Item(
        val isArchived: Boolean,
        val createdAt: Instant,
        val defaultBranch: String,
        val description: String?,
        val isDisabled: Boolean,
        val isFork: Boolean,
        val forks: Int,
        val forksCount: Int,
        val fullName: String,
        val hasDownloads: Boolean,
        val hasIssues: Boolean,
        val hasPages: Boolean,
        val hasProjects: Boolean,
        val hasWiki: Boolean,
        val homepage: String?,
        val id: Int,
        val language: String?,
        val name: String,
        val nodeId: String,
        val openIssues: Int,
        val openIssuesCount: Int,
        val owner: Owner,
        val isPrivate: Boolean,
        val pushedAt: Instant,
        val score: Float,
        val size: Int,
        val stargazersCount: Int,
        val topics: List<String>,
        val updatedAt: Instant,
        val url: String?,
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
            val isSiteAdmin: Boolean,
            val type: String,
            val url: String?,
        )
    }
}

fun SearchRepositoriesEntity.translate(): GhSearchRepositories {
    return GhSearchRepositories(
        isIncompleteResults = this.incompleteResults,
        totalCount = this.totalCount,
        items = this.items.map { it.translate() },
    )
}

fun SearchRepositoriesEntity.Item.translate(): GhSearchRepositories.Item {
    return GhSearchRepositories.Item(
        isArchived = this.archived,
        createdAt = Instant.parse(this.createdAt),
        defaultBranch = this.defaultBranch,
        description = this.description,
        isDisabled = this.disabled,
        isFork = this.fork,
        forks = this.forks,
        forksCount = this.forksCount,
        fullName = this.fullName,
        hasDownloads = this.hasDownloads,
        hasIssues = this.hasIssues,
        hasPages = this.hasPages,
        hasProjects = this.hasProjects,
        hasWiki = this.hasWiki,
        homepage = this.homepage,
        id = this.id,
        language = this.language,
        name = this.name,
        nodeId = this.nodeId,
        openIssues = this.openIssues,
        openIssuesCount = this.openIssuesCount,
        owner = this.owner.translate(),
        isPrivate = this.`private`,
        pushedAt = Instant.parse(this.pushedAt),
        score = this.score,
        size = this.size,
        stargazersCount = this.stargazersCount,
        topics = this.topics,
        updatedAt = Instant.parse(this.updatedAt),
        url = this.url,
        visibility = this.visibility,
        watchers = this.watchers,
        watchersCount = this.watchersCount,
    )
}

fun SearchRepositoriesEntity.Item.Owner.translate(): GhSearchRepositories.Item.Owner {
    return GhSearchRepositories.Item.Owner(
        avatarUrl = this.avatarUrl,
        gravatarId = this.gravatarId,
        id = this.id,
        login = this.login,
        nodeId = this.nodeId,
        isSiteAdmin = this.siteAdmin,
        type = this.type,
        url = this.url,
    )
}

fun GhSearchRepositories.Item.asRepositoryDetail(): GhRepositoryDetail {
    return GhRepositoryDetail(
        isArchived = this.isArchived,
        createdAt = this.createdAt,
        defaultBranch = this.defaultBranch,
        description = this.description,
        isDisabled = this.isDisabled,
        isFork = this.isFork,
        forks = this.forks,
        forksCount = this.forksCount,
        fullName = this.fullName,
        hasDownloads = this.hasDownloads,
        hasIssues = this.hasIssues,
        hasPages = this.hasPages,
        hasProjects = this.hasProjects,
        hasWiki = this.hasWiki,
        homepage = this.homepage,
        id = this.id,
        language = this.language,
        name = this.name,
        nodeId = this.nodeId,
        openIssues = this.openIssues,
        openIssuesCount = this.openIssuesCount,
        owner = this.owner.asRepositoryDetail(),
        isPrivate = this.isPrivate,
        pushedAt = this.pushedAt,
        size = this.size,
        stargazersCount = this.stargazersCount,
        topics = this.topics,
        updatedAt = this.updatedAt,
        visibility = this.visibility,
        watchers = this.watchers,
        watchersCount = this.watchersCount,
        hasDiscussions = false,
        isTemplate = false,
        networkCount = 0,
        parent = null,
        source = null,
        subscribersCount = 0,
        tempCloneToken = null,
        templateRepository = null,
    )
}

fun GhSearchRepositories.Item.Owner.asRepositoryDetail() = GhRepositoryDetail.Owner(
    avatarUrl = this.avatarUrl,
    gravatarId = this.gravatarId,
    id = this.id,
    login = this.login,
    nodeId = this.nodeId,
    type = this.type,
    url = this.url,
)
