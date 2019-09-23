package com.everis.workshop.data.network.entities

import com.everis.workshop.data.network.interfaces.ApiService

object UserDataSourceProvider {

    fun provideRequestUser(): UserDataSource = UserDataSource(ApiService.create())

}