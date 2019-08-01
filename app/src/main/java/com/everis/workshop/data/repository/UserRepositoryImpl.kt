package com.everis.workshop.data.repository

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.everis.workshop.data.network.entities.WsRequestUser
import com.everis.workshop.data.network.model.Result
import com.everis.workshop.data.network.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl(val wsRequestUser: WsRequestUser): UserRepository {

    override val user = MutableLiveData<User>()
    override val error = MutableLiveData<String>()

    override fun fetchUser() {
        wsRequestUser.requestUser().enqueue(object : Callback<Result> {
            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                user.postValue(response.body()!!.results[0])
            }
            override fun onFailure(call: Call<Result>?, t: Throwable) {
                error.postValue(t.message)
            }
        })
    }
}