package jp.co.yumemi.android.code_check.core.model

import jp.co.yumemi.android.code_check.core.model.entity.SearchRepositoriesEntity
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class SearchRepositories(
    val isIncompleteResults: Boolean,
    val items: List<GhRepositoryDetail>,
    val totalCount: Int,
)

fun SearchRepositoriesEntity.translate(): SearchRepositories {
    return SearchRepositories(
        isIncompleteResults = this.incompleteResults,
        totalCount = this.totalCount,
        items = this.items.map { it.translate() },
    )
}

fun SearchRepositoriesEntity.Item.translate(): GhRepositoryDetail {
    return GhRepositoryDetail(
        allowForking = false,
        allowMergeCommit = false,
        allowRebaseMerge = false,
        allowSquashMerge = false,
        allowAutoMerge = false,
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
        createdAt = Instant.parse(this.createdAt),
        defaultBranch = this.defaultBranch,
        deploymentsUrl = this.deploymentsUrl,
        description = this.description,
        isDeleteBranchOnMerge = false,
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
        hasDownloads = this.hasDownloads,
        hasIssues = this.hasIssues,
        hasPages = this.hasPages,
        hasProjects = this.hasProjects,
        hasWiki = this.hasWiki,
        hasDiscussions = false,
        homepage = this.homepage,
        hooksUrl = this.hooksUrl,
        htmlUrl = this.htmlUrl,
        id = this.id,
        issueCommentUrl = this.issueCommentUrl,
        issueEventsUrl = this.issueEventsUrl,
        issuesUrl = this.issuesUrl,
        isTemplate = false,
        keysUrl = this.keysUrl,
        labelsUrl = this.labelsUrl,
        language = this.language,
        languagesUrl = this.languagesUrl,
        license = this.license.translate(),
        mergesUrl = this.mergesUrl,
        milestonesUrl = this.milestonesUrl,
        mirrorUrl = this.mirrorUrl,
        name = this.name,
        nodeId = this.nodeId,
        notificationsUrl = this.notificationsUrl,
        openIssues = this.openIssues,
        openIssuesCount = this.openIssuesCount,
        owner = this.owner.translate(),
        isPrivate = this.`private`,
        pullsUrl = this.pullsUrl,
        pushedAt = Instant.parse(this.pushedAt),
        releasesUrl = this.releasesUrl,
        size = this.size,
        sshUrl = this.sshUrl,
        stargazersCount = this.stargazersCount,
        stargazersUrl = this.stargazersUrl,
        statusesUrl = this.statusesUrl,
        subscribersUrl = this.subscribersUrl,
        subscriptionUrl = this.subscriptionUrl,
        svnUrl = this.svnUrl,
        tagsUrl = this.tagsUrl,
        teamsUrl = this.teamsUrl,
        treesUrl = this.treesUrl,
        updatedAt = Instant.parse(this.updatedAt),
        url = this.url,
        visibility = this.visibility,
        watchers = this.watchers,
        watchersCount = this.watchersCount,
        networkCount = 0,
        subscribersCount = 0,
        organization = null,
        parent = null,
        source = null,
        permissions = null,
        templateRepository = null,
        tempCloneToken = "",
        topics = emptyList(),
    )
}

fun SearchRepositoriesEntity.Item.License.translate(): GhRepositoryDetail.License {
    return GhRepositoryDetail.License(
        key = this.key,
        name = this.name,
        nodeId = this.nodeId,
        spdxId = this.spdxId,
        url = this.url,
    )
}

fun SearchRepositoriesEntity.Item.Owner.translate(): GhRepositoryDetail.Owner {
    return GhRepositoryDetail.Owner(
        avatarUrl = this.avatarUrl,
        eventsUrl = this.eventsUrl,
        followersUrl = this.followersUrl,
        followingUrl = this.followingUrl,
        gistsUrl = this.gistsUrl,
        gravatarId = this.gravatarId,
        htmlUrl = this.htmlUrl,
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
}
