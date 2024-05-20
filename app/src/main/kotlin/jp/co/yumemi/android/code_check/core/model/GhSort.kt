package jp.co.yumemi.android.code_check.core.model

enum class GhUserSort(val value: String) {
    FOLLOWERS("followers"),
    REPOSITORIES("repositories"),
    JOINED("joined"),
}

enum class GhRepositorySort(val value: String) {
    STARS("stars"),
    FORKS("forks"),
    HELP_WANTED_ISSUES("help-wanted-issues"),
    UPDATED("updated"),
    ;

    companion object {
        fun fromValue(value: String): GhRepositorySort? {
            return when (value.lowercase()) {
                "stars" -> STARS
                "forks" -> FORKS
                "help-wanted-issues" -> HELP_WANTED_ISSUES
                "updated" -> UPDATED
                else -> null
            }
        }
    }
}
