package jp.co.yumemi.android.code_check.core.extensions.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val extensionsModule = module {
    single<CoroutineDispatcher> {
        Dispatchers.IO
    }
}
