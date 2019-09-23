package com.everis.workshop.ui.main.viewmodel

import android.app.Activity
import android.location.Location
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.everis.workshop.data.model.main.User
import com.everis.workshop.data.model.network.ResponseCase

interface MainViewModel {

    val user : LiveData<User>
    val error : LiveData<String>
    val location : LiveData<Location>
    val locationEnabled : LiveData<Boolean>
    val permissionGranted : LiveData<Boolean>

    val userResponseCase: LiveData<ResponseCase>

    fun getUserData()
    fun buttonAction(activity: Activity, objectAction: Object)
    fun initCurrentPosition(activity: FragmentActivity)
    fun checkLocation(activity: Activity)
    fun onRequestPermissionsResult(requestCode: Int,
                                   permissions: Array<String>, grantResults: IntArray)
}