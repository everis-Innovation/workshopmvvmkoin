package com.everis.workshop.ui.main.router

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.everis.workshop.data.model.map.MapCoordinates
import com.everis.workshop.ui.map.MapActivity
import com.everis.workshop.ui.map.MapFragment

class MainRouterImpl : MainRouter {

    override fun goMapView(activity: Activity, coor: MapCoordinates) {
        val intent = Intent(activity, MapActivity::class.java)
        var bundle = Bundle()
        bundle.putParcelable(MapFragment.COORDINATES_MAPS, coor)
        intent.putExtras(bundle)
        activity?.let {
            it.startActivity(intent)
        }
    }
}