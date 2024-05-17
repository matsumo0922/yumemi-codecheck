package jp.co.yumemi.android.code_check.core.model

data class YacBuildConfig(
    val versionCode: String,
    val versionName: String,
) {
    companion object {
        fun dummy(): YacBuildConfig {
            return YacBuildConfig(
                versionCode = "223",
                versionName = "1.4.21",
            )
        }
    }
}
