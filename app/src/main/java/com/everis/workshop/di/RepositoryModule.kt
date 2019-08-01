package com.everis.workshop.di

import com.everis.workshop.data.repository.UserRepository
import com.everis.workshop.data.repository.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { UserRepositoryImpl(get()) as UserRepository }
}