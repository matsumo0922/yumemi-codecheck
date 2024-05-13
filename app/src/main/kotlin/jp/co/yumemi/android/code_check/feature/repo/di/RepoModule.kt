package jp.co.yumemi.android.code_check.feature.repo.di

import jp.co.yumemi.android.code_check.feature.repo.RepositoryDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val repoModule = module {
    viewModelOf(::RepositoryDetailViewModel)
}
