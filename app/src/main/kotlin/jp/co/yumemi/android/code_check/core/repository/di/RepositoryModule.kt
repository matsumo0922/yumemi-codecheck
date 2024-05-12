package jp.co.yumemi.android.code_check.core.repository.di

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import jp.co.yumemi.android.code_check.core.extensions.formatter
import jp.co.yumemi.android.code_check.core.repository.ApiClient
import jp.co.yumemi.android.code_check.core.repository.GhApiRepository
import jp.co.yumemi.android.code_check.core.repository.GhApiRepositoryImpl
import jp.co.yumemi.android.code_check.core.repository.GhFavoriteRepository
import jp.co.yumemi.android.code_check.core.repository.GhFavoriteRepositoryImpl
import jp.co.yumemi.android.code_check.core.repository.GhSearchHistoryRepository
import jp.co.yumemi.android.code_check.core.repository.GhSearchHistoryRepositoryImpl
import jp.co.yumemi.android.code_check.core.repository.UserDataRepository
import jp.co.yumemi.android.code_check.core.repository.UserDataRepositoryImpl
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val repositoryModule = module {
    single<Json> {
        formatter
    }

    single {
        HttpClient {
            install(Logging) {
                level = LogLevel.INFO
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d(message)
                    }
                }
            }

            install(ContentNegotiation) {
                json(get<Json>())
            }
        }
    }

    single {
        ApiClient(
            client = get(),
            ioDispatcher = get(),
        )
    }

    single<UserDataRepository> {
        UserDataRepositoryImpl(
            userDataStore = get(),
        )
    }

    single<GhApiRepository> {
        GhApiRepositoryImpl(
            context = get(),
            ghCacheDataStore = get(),
            client = get(),
            ioDispatcher = get(),
        )
    }

    single<GhFavoriteRepository> {
        GhFavoriteRepositoryImpl(
            ghFavoriteDataStore = get(),
            ghCacheDataStore = get(),
            ghApiRepository = get(),
            ioDispatcher = get(),
        )
    }

    single<GhSearchHistoryRepository> {
        GhSearchHistoryRepositoryImpl(
            ghSearchHistoryDataStore = get(),
            ioDispatcher = get(),
        )
    }
}
