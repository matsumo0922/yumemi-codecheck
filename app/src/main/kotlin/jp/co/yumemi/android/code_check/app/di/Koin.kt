package jp.co.yumemi.android.code_check.app.di

import jp.co.yumemi.android.code_check.core.common.extensions.di.extensionsModule
import jp.co.yumemi.android.code_check.core.datastore.di.dataStoreModule
import jp.co.yumemi.android.code_check.core.repository.di.repositoryModule
import jp.co.yumemi.android.code_check.feature.home.di.homeModule
import jp.co.yumemi.android.code_check.feature.repo.di.repoModule
import jp.co.yumemi.android.code_check.feature.setting.di.settingModule
import org.koin.core.KoinApplication

fun KoinApplication.applyModules() {
    // app
    modules(appModule)

    // core
    modules(repositoryModule)
    modules(dataStoreModule)
    modules(extensionsModule)

    // feature
    modules(homeModule)
    modules(repoModule)
    modules(settingModule)
}
