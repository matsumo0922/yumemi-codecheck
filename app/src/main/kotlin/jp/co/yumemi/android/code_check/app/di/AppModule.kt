package jp.co.yumemi.android.code_check.app.di

import jp.co.yumemi.android.code_check.app.YacMainViewModel
import jp.co.yumemi.android.code_check.core.model.YacBuildConfig
import me.matsumo.yumemi.codecheck.BuildConfig
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::YacMainViewModel)

    single {
        YacBuildConfig(
            versionCode = BuildConfig.VERSION_CODE.toString(),
            versionName = BuildConfig.VERSION_NAME,
        )
    }
}
