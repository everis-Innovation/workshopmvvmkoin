package com.everis.workshop.data.network.entities

import com.everis.workshop.data.network.interfaces.ApiInterface
import com.everis.workshop.data.network.model.Result
import retrofit2.Call

class UserDataSource(private val apiService: ApiInterface) {

    fun requestUser(): Call<Result> = apiService.requestUser()

}