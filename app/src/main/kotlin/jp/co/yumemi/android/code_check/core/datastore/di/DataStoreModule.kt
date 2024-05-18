package jp.co.yumemi.android.code_check.core.datastore.di

import jp.co.yumemi.android.code_check.core.datastore.GhCacheDataStore
import jp.co.yumemi.android.code_check.core.datastore.GhFavoriteDataStore
import jp.co.yumemi.android.code_check.core.datastore.GhSearchHistoryDataStore
import jp.co.yumemi.android.code_check.core.datastore.PreferenceHelper
import jp.co.yumemi.android.code_check.core.datastore.PreferenceHelperImpl
import jp.co.yumemi.android.code_check.core.datastore.PreferenceName
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
            preference = get<PreferenceHelper>().create(PreferenceName.USER_DATA),
            formatter = get(),
        )
    }

    single {
        GhCacheDataStore(
            preference = get<PreferenceHelper>().create(PreferenceName.GH_CACHE),
            formatter = get(),
        )
    }

    single {
        GhFavoriteDataStore(
            preference = get<PreferenceHelper>().create(PreferenceName.GH_FAVORITE),
            formatter = get(),
        )
    }

    single {
        GhSearchHistoryDataStore(
            preference = get<PreferenceHelper>().create(PreferenceName.GH_SEARCH_HISTORY),
            formatter = get(),
        )
    }
}
