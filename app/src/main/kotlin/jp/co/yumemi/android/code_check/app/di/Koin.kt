package jp.co.yumemi.android.code_check.app.di

import jp.co.yumemi.android.code_check.core.datastore.di.dataStoreModule
import jp.co.yumemi.android.code_check.core.extensions.di.extensionsModule
import jp.co.yumemi.android.code_check.core.repository.di.repositoryModule
import org.koin.core.KoinApplication

fun KoinApplication.applyModules() {
    modules(appModule)

    modules(repositoryModule)
    modules(dataStoreModule)
    modules(extensionsModule)
}
