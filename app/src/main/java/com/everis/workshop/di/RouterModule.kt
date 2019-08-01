package com.everis.workshop.di

import com.everis.workshop.ui.main.router.MainRouter
import com.everis.workshop.ui.main.router.MainRouterImpl
import org.koin.dsl.module

val routerModule = module {
    single { MainRouterImpl() as MainRouter }
}