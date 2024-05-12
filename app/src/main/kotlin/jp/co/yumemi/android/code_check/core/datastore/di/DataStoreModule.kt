package jp.co.yumemi.android.code_check.core.datastore.di

import jp.co.yumemi.android.code_check.core.datastore.GhCacheDataStore
import jp.co.yumemi.android.code_check.core.datastore.GhFavoriteDataStore
import jp.co.yumemi.android.code_check.core.datastore.PreferenceHelper
import jp.co.yumemi.android.code_check.core.datastore.PreferenceHelperImpl
import jp.co.yumemi.android.code_check.core.datastore.UserDataStore
import org.koin.dsl.module

val dataStoreModule = module {
    single<PreferenceHelper> {
        PreferenceHelperImpl(
            context = get(),
            ioDispatcher = get(),
        )
    }

    single {
        UserDataStore(
            preferenceHelper = get(),
            formatter = get(),
            ioDispatcher = get(),
        )
    }

    single {
        GhCacheDataStore(
            preferenceHelper = get(),
            formatter = get(),
            ioDispatcher = get(),
        )
    }

    single {
        GhFavoriteDataStore(
            preferenceHelper = get(),
            formatter = get(),
            ioDispatcher = get(),
        )
    }
}
