package com.everis.workshop.data.network.entities

import com.everis.workshop.data.network.interfaces.ApiInterface
import com.everis.workshop.data.model.main.Result
import kotlinx.coroutines.Deferred
import retrofit2.Call

class UserDataSource(private val apiService: ApiInterface) {

    fun requestUser(): Deferred<Result> = apiService.requestUser()

}