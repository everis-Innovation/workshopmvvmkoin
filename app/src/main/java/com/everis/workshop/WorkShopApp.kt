package com.everis.workshop

import android.app.Application
import com.everis.workshop.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WorkShopApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // declare used Android context
            androidContext(this@WorkShopApp)
            // declare modules
            modules(listOf(routerModule, networkModule, repositoryModule, viewModelModule, viewModule))
        }
    }
}