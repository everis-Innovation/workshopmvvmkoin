package com.everis.workshop.di

import com.everis.workshop.data.network.entities.UserDataSource
import com.everis.workshop.data.network.interfaces.ApiService
import org.koin.dsl.module

val networkModule = module {
    single { UserDataSource(get()) }
    single { ApiService.create() }
}