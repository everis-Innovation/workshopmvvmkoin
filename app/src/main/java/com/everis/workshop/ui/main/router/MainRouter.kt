package com.everis.workshop.ui.main.router

import android.app.Activity
import com.everis.workshop.data.model.map.MapCoordinates

interface MainRouter {
    fun goMapView(activity: Activity, coor: MapCoordinates)
}