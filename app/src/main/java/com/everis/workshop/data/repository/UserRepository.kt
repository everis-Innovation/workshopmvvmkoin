package com.everis.workshop.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.everis.workshop.data.model.main.User
import com.everis.workshop.data.model.network.ResponseCase

interface UserRepository {

    val user: MutableLiveData<User>
    val error: MutableLiveData<String>

    val userResponseCase: LiveData<ResponseCase>
    fun fetchUser()
}