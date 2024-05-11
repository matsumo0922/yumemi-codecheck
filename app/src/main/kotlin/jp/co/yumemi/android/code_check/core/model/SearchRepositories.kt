package jp.co.yumemi.android.code_check.core.model

import jp.co.yumemi.android.code_check.core.model.entity.SearchRepositoriesEntity
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class SearchRepositories(
    val isIncompleteResults: Boolean,
    val items: List<Item>,
    val totalCount: Int,
) {
    @Serializable
    data class Item(
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
        val deploymentsUrl: String,
        val description: String,
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
        val hasDownloads: Boolean,
        val hasIssues: Boolean,
        val hasPages: Boolean,
        val hasProjects: Boolean,
        val hasWiki: Boolean,
        val homepage: String,
        val hooksUrl: String,
        val htmlUrl: String,
        val id: Int,
        val issueCommentUrl: String,
        val issueEventsUrl: String,
        val issuesUrl: String,
        val keysUrl: String,
        val labelsUrl: String,
        val language: String,
        val languagesUrl: String,
        val license: License,
        val masterBranch: String,
        val mergesUrl: String,
        val milestonesUrl: String,
        val mirrorUrl: String,
        val name: String,
        val nodeId: String,
        val notificationsUrl: String,
        val openIssues: Int,
        val openIssuesCount: Int,
        val owner: Owner,
        val isPrivate: Boolean,
        val pullsUrl: String,
        val pushedAt: Instant,
        val releasesUrl: String,
        val score: Int,
        val size: Int,
        val sshUrl: String,
        val stargazersCount: Int,
        val stargazersUrl: String,
        val statusesUrl: String,
        val subscribersUrl: String,
        val subscriptionUrl: String,
        val svnUrl: String,
        val tagsUrl: String,
        val teamsUrl: String,
        val treesUrl: String,
        val updatedAt: Instant,
        val url: String,
        val visibility: String,
        val watchers: Int,
        val watchersCount: Int,
    ) {
        @Serializable
        data class License(
            val htmlUrl: String,
            val key: String,
            val name: String,
            val nodeId: String,
            val spdxId: String,
            val url: String,
        )

        @Serializable
        data class Owner(
            val avatarUrl: String,
            val eventsUrl: String,
            val followersUrl: String,
            val followingUrl: String,
            val gistsUrl: String,
            val gravatarId: String,
            val htmlUrl: String,
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
            val url: String,
        )
    }
}

fun SearchRepositoriesEntity.translate(): SearchRepositories {
    return SearchRepositories(
        isIncompleteResults = this.incompleteResults,
        totalCount = this.totalCount,
        items = this.items.map { it.translate() },
    )
}

fun SearchRepositoriesEntity.Item.translate(): SearchRepositories.Item {
    return SearchRepositories.Item(
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
        homepage = this.homepage,
        hooksUrl = this.hooksUrl,
        htmlUrl = this.htmlUrl,
        id = this.id,
        issueCommentUrl = this.issueCommentUrl,
        issueEventsUrl = this.issueEventsUrl,
        issuesUrl = this.issuesUrl,
        keysUrl = this.keysUrl,
        labelsUrl = this.labelsUrl,
        language = this.language,
        languagesUrl = this.languagesUrl,
        license = this.license.translate(),
        masterBranch = this.masterBranch,
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
        score = this.score,
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
    )
}

fun SearchRepositoriesEntity.Item.License.translate(): SearchRepositories.Item.License {
    return SearchRepositories.Item.License(
        htmlUrl = this.htmlUrl,
        key = this.key,
        name = this.name,
        nodeId = this.nodeId,
        spdxId = this.spdxId,
        url = this.url,
    )
}

fun SearchRepositoriesEntity.Item.Owner.translate(): SearchRepositories.Item.Owner {
    return SearchRepositories.Item.Owner(
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
