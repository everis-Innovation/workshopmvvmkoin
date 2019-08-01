package com.everis.workshop.di

import com.everis.workshop.data.network.entities.WsRequestUser
import com.everis.workshop.data.network.interfaces.ApiService
import org.koin.dsl.module

val networkModule = module {
    single { WsRequestUser(get()) }
    single { ApiService.create() }
}