package jp.co.yumemi.android.code_check.feature.setting.di

import jp.co.yumemi.android.code_check.feature.setting.theme.SettingThemeViewModel
import jp.co.yumemi.android.code_check.feature.setting.top.SettingTopViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val settingModule = module {
    viewModelOf(::SettingTopViewModel)
    viewModelOf(::SettingThemeViewModel)
}
