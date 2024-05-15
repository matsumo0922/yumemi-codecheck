package jp.co.yumemi.android.code_check.core.model

import jp.co.yumemi.android.code_check.core.model.entity.GhTrendRepositoryEntity
import kotlinx.serialization.Serializable

@Serializable
data class GhTrendRepository(
    val author: String,
    val avatar: String,
    val builtBy: List<BuiltBy>,
    val currentPeriodStars: Int,
    val description: String,
    val forks: Int,
    val language: String,
    val languageColor: String,
    val name: String,
    val stars: Int,
    val url: String
) {
    val repoName: GhRepositoryName = GhRepositoryName(
        owner = this.author,
        name = this.name,
    )

    @Serializable
    data class BuiltBy(
        val avatar: String,
        val href: String,
        val username: String
    )
}

fun List<GhTrendRepositoryEntity>.translate(): List<GhTrendRepository> {
    return map {
        GhTrendRepository(
            author = it.author,
            avatar = it.avatar,
            builtBy = it.builtBy.map { builtBy ->
                GhTrendRepository.BuiltBy(
                    avatar = builtBy.avatar,
                    href = builtBy.href,
                    username = builtBy.username
                )
            },
            currentPeriodStars = it.currentPeriodStars,
            description = it.description,
            forks = it.forks,
            language = it.language,
            languageColor = it.languageColor,
            name = it.name,
            stars = it.stars,
            url = it.url
        )
    }
}
