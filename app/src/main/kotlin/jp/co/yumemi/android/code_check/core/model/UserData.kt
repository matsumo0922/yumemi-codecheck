package jp.co.yumemi.android.code_check.core.model

data class UserData(
        val themeConfig: ThemeConfig,
        val themeColorConfig: ThemeColorConfig,
        val isAgreedPrivacyPolicy: Boolean,
        val isAgreedTermsOfService: Boolean,
        val isUseDynamicColor: Boolean,
)
