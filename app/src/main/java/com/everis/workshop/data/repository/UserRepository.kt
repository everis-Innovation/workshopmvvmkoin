package com.everis.workshop.data.repository

import androidx.lifecycle.MutableLiveData
import com.everis.workshop.data.network.model.User

interface UserRepository {

    val user: MutableLiveData<User>
    val error: MutableLiveData<String>

    fun fetchUser()
}