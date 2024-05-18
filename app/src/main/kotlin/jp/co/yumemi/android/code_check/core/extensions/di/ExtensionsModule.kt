package jp.co.yumemi.android.code_check.core.extensions.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module

@OptIn(ExperimentalCoroutinesApi::class)
val extensionsModule = module {
    single<CoroutineDispatcher> {
        Dispatchers.IO.limitedParallelism(100)
    }
}
