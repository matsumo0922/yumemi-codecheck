package jp.co.yumemi.android.code_check.core.model

import jp.co.yumemi.android.code_check.core.model.entity.RepositoryDetailEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.toInstant
import kotlinx.serialization.Serializable

@Serializable
data class GhRepositoryDetail(
    val allowAutoMerge: Boolean,
    val allowForking: Boolean,
    val allowMergeCommit: Boolean,
    val allowRebaseMerge: Boolean,
    val allowSquashMerge: Boolean,
    val archiveUrl: String,
    val isArchived: Boolean,
    val assigneesUrl: String,
    val blobsUrl: String,
    val branchesUrl: String,
    val cloneUrl: String,
    val collaboratorsUrl: String,
    val commentsUrl: String,
    val commitsUrl: String,
    val compareUrl: String,
    val contentsUrl: String,
    val contributorsUrl: String,
    val createdAt: Instant,
    val defaultBranch: String,
    val isDeleteBranchOnMerge: Boolean,
    val deploymentsUrl: String,
    val description: String?,
    val isDisabled: Boolean,
    val downloadsUrl: String,
    val eventsUrl: String,
    val isFork: Boolean,
    val forks: Int,
    val forksCount: Int,
    val forksUrl: String,
    val fullName: String,
    val gitCommitsUrl: String,
    val gitRefsUrl: String,
    val gitTagsUrl: String,
    val gitUrl: String,
    val hasDiscussions: Boolean,
    val hasDownloads: Boolean,
    val hasIssues: Boolean,
    val hasPages: Boolean,
    val hasProjects: Boolean,
    val hasWiki: Boolean,
    val homepage: String?,
    val hooksUrl: String,
    val id: Int,
    val isTemplate: Boolean,
    val issueCommentUrl: String,
    val issueEventsUrl: String,
    val issuesUrl: String,
    val keysUrl: String,
    val labelsUrl: String,
    val languagesUrl: String,
    val license: License?,
    val mergesUrl: String,
    val milestonesUrl: String,
    val mirrorUrl: String?,
    val name: String,
    val networkCount: Int,
    val nodeId: String,
    val notificationsUrl: String,
    val language: String?,
    val openIssues: Int,
    val openIssuesCount: Int,
    val organization: Organization?,
    val owner: Owner,
    val parent: GhRepositoryDetail?,
    val permissions: Permissions?,
    val isPrivate: Boolean,
    val pullsUrl: String,
    val pushedAt: Instant,
    val releasesUrl: String,
    val size: Int,
    val source: GhRepositoryDetail?,
    val sshUrl: String,
    val stargazersCount: Int,
    val stargazersUrl: String,
    val statusesUrl: String,
    val subscribersCount: Int,
    val subscribersUrl: String,
    val subscriptionUrl: String,
    val svnUrl: String,
    val tagsUrl: String,
    val teamsUrl: String,
    val tempCloneToken: String,
    val templateRepository: GhRepositoryDetail?,
    val topics: List<String>,
    val treesUrl: String,
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
    data class License(
        val key: String,
        val name: String,
        val nodeId: String,
        val spdxId: String,
        val url: String?,
    )

    @Serializable
    data class Organization(
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
        val siteAdmin: Boolean,
        val starredUrl: String,
        val subscriptionsUrl: String,
        val type: String,
        val url: String?,
    )

    @Serializable
    data class Owner(
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
        val isSiteAdmin: Boolean,
        val starredUrl: String,
        val subscriptionsUrl: String,
        val type: String,
        val url: String?,
    )

    @Serializable
    data class Permissions(
        val admin: Boolean,
        val pull: Boolean,
        val push: Boolean,
    )
}

// Extension function to translate DTO to Model
fun RepositoryDetailEntity.translate(): GhRepositoryDetail {
    return GhRepositoryDetail(
        allowAutoMerge = this.allowAutoMerge,
        allowForking = this.allowForking,
        allowMergeCommit = this.allowMergeCommit,
        allowRebaseMerge = this.allowRebaseMerge,
        allowSquashMerge = this.allowSquashMerge,
        archiveUrl = this.archiveUrl,
        isArchived = this.archived,
        assigneesUrl = this.assigneesUrl,
        blobsUrl = this.blobsUrl,
        branchesUrl = this.branchesUrl,
        cloneUrl = this.cloneUrl,
        collaboratorsUrl = this.collaboratorsUrl,
        commentsUrl = this.commentsUrl,
        commitsUrl = this.commitsUrl,
        compareUrl = this.compareUrl,
        contentsUrl = this.contentsUrl,
        contributorsUrl = this.contributorsUrl,
        createdAt = this.createdAt.toInstant(),
        defaultBranch = this.defaultBranch,
        isDeleteBranchOnMerge = this.deleteBranchOnMerge,
        deploymentsUrl = this.deploymentsUrl,
        description = this.description,
        isDisabled = this.disabled,
        downloadsUrl = this.downloadsUrl,
        eventsUrl = this.eventsUrl,
        isFork = this.fork,
        forks = this.forks,
        forksCount = this.forksCount,
        forksUrl = this.forksUrl,
        fullName = this.fullName,
        gitCommitsUrl = this.gitCommitsUrl,
        gitRefsUrl = this.gitRefsUrl,
        gitTagsUrl = this.gitTagsUrl,
        gitUrl = this.gitUrl,
        hasDiscussions = this.hasDiscussions,
        hasDownloads = this.hasDownloads,
        hasIssues = this.hasIssues,
        hasPages = this.hasPages,
        hasProjects = this.hasProjects,
        hasWiki = this.hasWiki,
        homepage = this.homepage,
        hooksUrl = this.hooksUrl,
        id = this.id,
        isTemplate = this.isTemplate,
        issueCommentUrl = this.issueCommentUrl,
        issueEventsUrl = this.issueEventsUrl,
        issuesUrl = this.issuesUrl,
        keysUrl = this.keysUrl,
        labelsUrl = this.labelsUrl,
        languagesUrl = this.languagesUrl,
        license = this.license?.translate(),
        mergesUrl = this.mergesUrl,
        milestonesUrl = this.milestonesUrl,
        mirrorUrl = this.mirrorUrl,
        name = this.name,
        networkCount = this.networkCount,
        nodeId = this.nodeId,
        notificationsUrl = this.notificationsUrl,
        language = "",
        openIssues = this.openIssues,
        openIssuesCount = this.openIssuesCount,
        organization = this.organization.translate(),
        owner = this.owner.translate(),
        parent = this.parent?.translate(),
        permissions = this.permissions.translate(),
        isPrivate = this.private,
        pullsUrl = this.pullsUrl,
        pushedAt = this.pushedAt.toInstant(),
        releasesUrl = this.releasesUrl,
        size = this.size,
        source = this.source?.translate(),
        sshUrl = this.sshUrl,
        stargazersCount = this.stargazersCount,
        stargazersUrl = this.stargazersUrl,
        statusesUrl = this.statusesUrl,
        subscribersCount = this.subscribersCount,
        subscribersUrl = this.subscribersUrl,
        subscriptionUrl = this.subscriptionUrl,
        svnUrl = this.svnUrl,
        tagsUrl = this.tagsUrl,
        teamsUrl = this.teamsUrl,
        tempCloneToken = this.tempCloneToken,
        templateRepository = this.templateRepository?.translate(),
        topics = this.topics,
        treesUrl = this.treesUrl,
        updatedAt = this.updatedAt.toInstant(),
        url = this.url,
        visibility = this.visibility,
        watchers = this.watchers,
        watchersCount = this.watchersCount,
    )
}

// Nested classes also require translation functions if they contain nested data types
fun RepositoryDetailEntity.License.translate() = GhRepositoryDetail.License(
    key = this.key,
    name = this.name,
    nodeId = this.nodeId,
    spdxId = this.spdxId,
    url = this.url,
)

fun RepositoryDetailEntity.Organization.translate() = GhRepositoryDetail.Organization(
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
    siteAdmin = this.siteAdmin,
    starredUrl = this.starredUrl,
    subscriptionsUrl = this.subscriptionsUrl,
    type = this.type,
    url = this.url,
)

fun RepositoryDetailEntity.Owner.translate() = GhRepositoryDetail.Owner(
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
    isSiteAdmin = this.siteAdmin,
    starredUrl = this.starredUrl,
    subscriptionsUrl = this.subscriptionsUrl,
    type = this.type,
    url = this.url,
)

fun RepositoryDetailEntity.Permissions.translate() = GhRepositoryDetail.Permissions(
    admin = this.admin,
    pull = this.pull,
    push = this.push,
)
