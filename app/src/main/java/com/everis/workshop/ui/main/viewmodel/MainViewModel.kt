package com.everis.workshop.ui.main.viewmodel

import android.app.Activity
import android.location.Location
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.everis.workshop.data.network.model.User
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener

interface MainViewModel {

    val user : LiveData<User>
    val error : LiveData<String>
    val location : LiveData<Location>
    val locationEnabled : LiveData<Boolean>
    val permissionGranted : LiveData<Boolean>

    fun getUserData()
    fun buttonAction(activity: Activity, objectAction: Object)
    fun initCurrentPosition(activity: FragmentActivity)
    fun checkLocation(activity: Activity)
    fun onRequestPermissionsResult(requestCode: Int,
                                   permissions: Array<String>, grantResults: IntArray)
}