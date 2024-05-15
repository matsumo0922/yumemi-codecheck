package jp.co.yumemi.android.code_check.core.ui.previews

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import jp.co.yumemi.android.code_check.core.model.GhTrendRepository

class GhTrendRepositoryPreviewParameter : PreviewParameterProvider<GhTrendRepository> {

    override val values: Sequence<GhTrendRepository>
        get() = sequenceOf(dummy)

    private val dummy = GhTrendRepository(
        author = "author",
        avatar = "avatar",
        builtBy = emptyList(),
        currentPeriodStars = 0,
        description = "description",
        forks = 0,
        language = "Kotlin",
        languageColor = "#A97BFF",
        name = "sample-repository",
        stars = 0,
        url = "url",
    )
}
