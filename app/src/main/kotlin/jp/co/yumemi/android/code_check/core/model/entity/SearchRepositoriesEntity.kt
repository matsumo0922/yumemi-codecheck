package jp.co.yumemi.android.code_check.core.model.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchRepositoriesEntity(
    @SerialName("incomplete_results")
    val incompleteResults: Boolean,
    @SerialName("items")
    val items: List<Item>,
    @SerialName("total_count")
    val totalCount: Int,
) {
    @Serializable
    data class Item(
        @SerialName("archived")
        val archived: Boolean,
        @SerialName("created_at")
        val createdAt: String,
        @SerialName("default_branch")
        val defaultBranch: String,
        @SerialName("description")
        val description: String?,
        @SerialName("disabled")
        val disabled: Boolean,
        @SerialName("fork")
        val fork: Boolean,
        @SerialName("forks")
        val forks: Int,
        @SerialName("forks_count")
        val forksCount: Int,
        @SerialName("full_name")
        val fullName: String,
        @SerialName("has_downloads")
        val hasDownloads: Boolean,
        @SerialName("has_issues")
        val hasIssues: Boolean,
        @SerialName("has_pages")
        val hasPages: Boolean,
        @SerialName("has_projects")
        val hasProjects: Boolean,
        @SerialName("has_wiki")
        val hasWiki: Boolean,
        @SerialName("homepage")
        val homepage: String?,
        @SerialName("id")
        val id: Int,
        @SerialName("language")
        val language: String?,
        @SerialName("license")
        val license: License?,
        @SerialName("name")
        val name: String,
        @SerialName("node_id")
        val nodeId: String,
        @SerialName("open_issues")
        val openIssues: Int,
        @SerialName("open_issues_count")
        val openIssuesCount: Int,
        @SerialName("owner")
        val owner: Owner,
        @SerialName("private")
        val `private`: Boolean,
        @SerialName("pushed_at")
        val pushedAt: String,
        @SerialName("score")
        val score: Float,
        @SerialName("size")
        val size: Int,
        @SerialName("stargazers_count")
        val stargazersCount: Int,
        @SerialName("topics")
        val topics: List<String>,
        @SerialName("updated_at")
        val updatedAt: String,
        @SerialName("url")
        val url: String?,
        @SerialName("visibility")
        val visibility: String,
        @SerialName("watchers")
        val watchers: Int,
        @SerialName("watchers_count")
        val watchersCount: Int,
    ) {
        @Serializable
        data class License(
            @SerialName("key")
            val key: String,
            @SerialName("name")
            val name: String,
            @SerialName("node_id")
            val nodeId: String,
            @SerialName("spdx_id")
            val spdxId: String,
            @SerialName("url")
            val url: String?,
        )

        @Serializable
        data class Owner(
            @SerialName("avatar_url")
            val avatarUrl: String,
            @SerialName("gravatar_id")
            val gravatarId: String,
            @SerialName("id")
            val id: Int,
            @SerialName("login")
            val login: String,
            @SerialName("node_id")
            val nodeId: String,
            @SerialName("site_admin")
            val siteAdmin: Boolean,
            @SerialName("type")
            val type: String,
            @SerialName("url")
            val url: String?,
        )
    }
}
