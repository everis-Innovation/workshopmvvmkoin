package com.everis.workshop.data.repository

import androidx.lifecycle.MutableLiveData
import com.everis.workshop.data.network.entities.UserDataSource
import com.everis.workshop.data.model.main.Result
import com.everis.workshop.data.model.main.User
import com.everis.workshop.data.model.network.ResponseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl(val userDataSource: UserDataSource): UserRepository {

    override val userResponseCase = MutableLiveData<ResponseCase>()

    override val user = MutableLiveData<User>()
    override val error = MutableLiveData<String>()


    override fun fetchUser() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val job =  userDataSource.requestUser()
                val response = job.await()
                user.postValue(response.results[0])
                userResponseCase.postValue(ResponseCase.UserResponse(response.results[0]))

            }catch (e: Exception) {
                error.postValue(e.message)
                userResponseCase.postValue(ResponseCase.ErrorResponse(e.message!!))

            }
        }
    }
}