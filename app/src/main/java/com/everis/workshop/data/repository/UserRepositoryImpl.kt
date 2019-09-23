package com.everis.workshop.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.everis.workshop.data.network.entities.UserDataSource
import com.everis.workshop.data.model.main.Result
import com.everis.workshop.data.model.main.User
import com.everis.workshop.data.model.network.ResponseCase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl(val userDataSource: UserDataSource): UserRepository {

    override val userResponseCase = MutableLiveData<ResponseCase>()

    override val user = MutableLiveData<User>()
    override val error = MutableLiveData<String>()


    override fun fetchUser() {
        userDataSource.requestUser().enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                user.postValue(response.body()!!.results[0])
                userResponseCase.postValue(ResponseCase.UserResponse(response.body()!!.results[0]))
            }
            override fun onFailure(call: Call<Result>?, t: Throwable) {
                error.postValue(t.message)
                userResponseCase.postValue(ResponseCase.ErrorResponse(t.message!!))
            }
        })
    }
}