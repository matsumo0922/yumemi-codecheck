package jp.co.yumemi.android.code_check.core.repository.di

import jp.co.yumemi.android.code_check.core.repository.UserDataRepository
import jp.co.yumemi.android.code_check.core.repository.UserDataRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UserDataRepository> {
        UserDataRepositoryImpl(
            userDataStore = get(),
        )
    }
}
