package com.everis.workshop.di

import com.everis.workshop.ui.main.view.MainActivity
import com.everis.workshop.ui.main.view.MainFragment
import com.everis.workshop.ui.main.view.MainView
import org.koin.dsl.module

val viewModule = module {
    single { MainFragment() as MainView}
    single { MainActivity() }
}