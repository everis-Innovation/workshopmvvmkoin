package com.everis.workshop.data.network.interfaces

import com.everis.workshop.data.model.main.Result
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiInterface {

    @Headers("dataType: \"json\"")
    @GET("api/")
    fun requestUser(): Deferred<Result>

}