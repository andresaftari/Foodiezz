package com.andresaftari.foodiezz.di

import com.andresaftari.core.domain.usecase.CoreInteractor
import com.andresaftari.core.domain.usecase.IMealUseCase
import com.andresaftari.foodiezz.view.detail.DetailViewModel
import com.andresaftari.foodiezz.view.home.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<IMealUseCase> { CoreInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}