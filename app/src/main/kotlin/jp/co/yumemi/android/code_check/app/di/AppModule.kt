package jp.co.yumemi.android.code_check.app.di

import jp.co.yumemi.android.code_check.app.YacMainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::YacMainViewModel)
}
