package com.everis.workshop.utils.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*

class LocationUtils {

    companion object {
        val REQUEST_LOCATION_PERMISSION = 222
        private val UPDATE_INTERVAL = (2 * 1000).toLong()  /* 10 secs */
        private val FASTEST_INTERVAL: Long = 2000 /* 2 sec */

        fun isLocationEnabled(activity: Activity): Boolean {
            val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        }

        fun initCurrentPosition(
            activity: FragmentActivity,
            position: MutableLiveData<Location>) {

            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

                val permissions =
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                ActivityCompat.requestPermissions(activity, permissions, REQUEST_LOCATION_PERMISSION)
                return
            }
            val fusedLocationProviderClient:
                    FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
            initUpdateCurrentPosition(activity, position, fusedLocationProviderClient)

            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener(activity) { location ->
                    if (location != null) {
                        position.value = location
                    }
                }
        }

        private fun initUpdateCurrentPosition(
            activity: Activity,
            position: MutableLiveData<Location>,
            fusedLocationProviderClient: FusedLocationProviderClient
        ) {
            // Create the location request
            val locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)
            // Request location updates
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                return
            }

            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    position.value = locationResult.lastLocation
                }
            }

            fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                locationCallback, Looper.myLooper())
            /*LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient,
                locationRequest, locationListener)*/
        }

    }
}