package jp.co.yumemi.android.code_check.core.repository.di

import jp.co.yumemi.android.code_check.core.extensions.formatter
import jp.co.yumemi.android.code_check.core.repository.UserDataRepository
import jp.co.yumemi.android.code_check.core.repository.UserDataRepositoryImpl
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val repositoryModule = module {
    single<Json> {
        formatter
    }

    single<UserDataRepository> {
        UserDataRepositoryImpl(
            userDataStore = get(),
        )
    }
}
