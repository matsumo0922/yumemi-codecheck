package jp.co.yumemi.android.code_check.core.ui.previews

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import jp.co.yumemi.android.code_check.core.model.GhUserDetail
import kotlinx.datetime.Instant

class GhUserDetailPreviewParameter : PreviewParameterProvider<GhUserDetail> {

    override val values: Sequence<GhUserDetail>
        get() = sequenceOf(dummy)

    companion object {
        val dummy =  GhUserDetail(
            avatarUrl = "https://example.com/avatar.png",
            bio = "Software Developer with a passion for open-source.",
            blog = "https://example.com/blog",
            company = "Example Corp",
            createdAt = Instant.parse("2023-01-01T00:00:00Z"),
            email = "user@example.com",
            eventsUrl = "https://api.example.com/users/user/events",
            followers = 150,
            followersUrl = "https://api.example.com/users/user/followers",
            following = 100,
            followingUrl = "https://api.example.com/users/user/following",
            gistsUrl = "https://api.example.com/users/user/gists",
            gravatarId = "gravatarid12345",
            hireable = true,
            id = 123456,
            location = "San Francisco, CA",
            login = "username",
            name = "John Doe",
            nodeId = "MDQ6VXNlcjEyMzQ1Ng==",
            organizationsUrl = "https://api.example.com/users/user/orgs",
            publicGists = 5,
            publicRepos = 20,
            receivedEventsUrl = "https://api.example.com/users/user/received_events",
            reposUrl = "https://api.example.com/users/user/repos",
            siteAdmin = false,
            starredUrl = "https://api.example.com/users/user/starred{/owner}{/repo}",
            twitterUsername = "johndoe",
            type = "User",
            updatedAt = Instant.parse("2024-01-01T00:00:00Z"),
            url = "https://api.example.com/users/user"
        )

    }
}
