package jp.co.yumemi.android.code_check.feature.home.di

import jp.co.yumemi.android.code_check.feature.home.favorite.HomeFavoriteViewModel
import jp.co.yumemi.android.code_check.feature.home.search.HomeSearchViewModel
import jp.co.yumemi.android.code_check.feature.home.trend.HomeTrendViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeSearchViewModel)
    viewModelOf(::HomeTrendViewModel)
    viewModelOf(::HomeFavoriteViewModel)
}
