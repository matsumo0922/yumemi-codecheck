package jp.co.yumemi.android.code_check.core.model

import kotlinx.serialization.Serializable

@Serializable
data class UserData(
    val themeConfig: ThemeConfig,
    val themeColorConfig: ThemeColorConfig,
    val trendSince: String,
    val trendLanguage: String,
    val isAgreedPrivacyPolicy: Boolean,
    val isAgreedTermsOfService: Boolean,
    val isUseDynamicColor: Boolean,
) {
    companion object {
        fun default(): UserData {
            return UserData(
                themeConfig = ThemeConfig.System,
                themeColorConfig = ThemeColorConfig.Blue,
                trendSince = GhTrendSince.DAILY.value,
                trendLanguage = "",
                isAgreedPrivacyPolicy = false,
                isAgreedTermsOfService = false,
                isUseDynamicColor = false,
            )
        }
    }
}
