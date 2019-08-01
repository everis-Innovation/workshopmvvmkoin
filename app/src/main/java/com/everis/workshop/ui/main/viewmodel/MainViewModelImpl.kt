package com.everis.workshop.ui.main.viewmodel

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.everis.workshop.data.model.map.MapCoordinates
import com.everis.workshop.data.network.model.User
import com.everis.workshop.data.repository.UserRepository
import com.everis.workshop.ui.main.router.MainRouter
import com.everis.workshop.ui.main.router.MainRouterImpl
import com.everis.workshop.ui.main.view.MainView
import com.everis.workshop.utils.location.LocationUtils
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainViewModelImpl(private val mainView: MainView,
                        private val remoteRepository: UserRepository) : MainViewModel, ViewModel() {

    override val user: LiveData<User> = remoteRepository.user
    override val error: LiveData<String> = remoteRepository.error

    private val locationMutable = MutableLiveData<Location>()
    override val location: LiveData<Location> = locationMutable

    private val locationEnabledMutable = MutableLiveData<Boolean>()
    override val locationEnabled: LiveData<Boolean> = locationEnabledMutable

    private val permissionGrantedMutable = MutableLiveData<Boolean>()
    override val permissionGranted: LiveData<Boolean> = permissionGrantedMutable

    private val TAG = "Location request"

    private val router: MainRouter by (mainView as Fragment).inject()

   /* companion object {
        /**
         * Factory for creating [MainViewModel]
         */
        val FACTORY = singleArgViewModelFactory(::MainViewModelImpl)
    }*/

    init {
        locationMutable.value = Location("0,0")
    }

    override fun getUserData() {
        remoteRepository.fetchUser()
    }

    override fun buttonAction(activity: Activity, objectAction: Object) {
         router.goMapView(activity, objectAction as MapCoordinates)
    }

    override fun initCurrentPosition(activity: FragmentActivity) {
       LocationUtils.initCurrentPosition(activity, locationMutable)
    }

    override fun checkLocation(activity: Activity) {
        locationEnabledMutable.value = LocationUtils.isLocationEnabled(activity)
    }


    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            LocationUtils.REQUEST_LOCATION_PERMISSION -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED
                    && grantResults[1] != PackageManager.PERMISSION_GRANTED) {
                    permissionGrantedMutable.value = true
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    permissionGrantedMutable.value = false
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
    }
}
