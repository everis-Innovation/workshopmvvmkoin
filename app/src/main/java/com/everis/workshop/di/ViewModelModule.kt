package com.everis.workshop.di

import com.everis.workshop.ui.main.viewmodel.MainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModelImpl(get()) }
}