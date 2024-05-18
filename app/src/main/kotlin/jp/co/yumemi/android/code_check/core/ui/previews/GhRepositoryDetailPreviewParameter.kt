package jp.co.yumemi.android.code_check.core.ui.previews

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import jp.co.yumemi.android.code_check.core.model.GhRepositoryDetail
import kotlinx.datetime.Instant

class GhRepositoryDetailPreviewParameter : PreviewParameterProvider<GhRepositoryDetail> {

    override val values: Sequence<GhRepositoryDetail>
        get() = sequenceOf(dummy)

    companion object {
        val dummy = GhRepositoryDetail(
            isArchived = false,
            createdAt = Instant.parse("2023-05-01T12:00:00Z"),
            defaultBranch = "main",
            description = "This is a sample repository",
            isDisabled = false,
            isFork = false,
            forks = 5,
            forksCount = 5,
            fullName = "example-user/sample-repo",
            hasDiscussions = true,
            hasDownloads = true,
            hasIssues = true,
            hasPages = false,
            hasProjects = true,
            hasWiki = true,
            homepage = "https://example.com",
            id = 12345,
            isTemplate = false,
            name = "sample-repo",
            networkCount = 1,
            nodeId = "MDEwOlJlcG9zaXRvcnkxMjM0NTY=",
            language = "Kotlin",
            openIssues = 10,
            openIssuesCount = 10,
            owner = GhRepositoryDetail.Owner(
                avatarUrl = "https://avatars.githubusercontent.com/u/123?v=4",
                gravatarId = "",
                id = 123,
                login = "example-user",
                nodeId = "MDQ6VXNlcjEyMw==",
                type = "User",
                url = "https://api.github.com/users/example",
            ),
            parent = null,
            isPrivate = false,
            pushedAt = Instant.parse("2023-05-10T12:00:00Z"),
            size = 365,
            source = null,
            stargazersCount = 100,
            subscribersCount = 42,
            tempCloneToken = null,
            templateRepository = null,
            topics = listOf("kotlin", "android", "open-source"),
            updatedAt = Instant.parse("2023-05-11T15:00:00Z"),
            visibility = "public",
            watchers = 150,
            watchersCount = 150,
        )
    }
}
