package com.everis.workshop.utils

import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.widget.Toast

fun Context.toast(message: CharSequence, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun Location.isLocationEnabled(activity: Activity): Boolean {
    val locationManager = activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

fun <T> List<T>.ordenar(t: T) {

}
