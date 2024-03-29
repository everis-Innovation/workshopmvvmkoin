package com.everis.workshop.ui.main

import android.location.Location
import android.os.Bundle
import com.everis.workshop.data.model.main.Result
import com.everis.workshop.ui.base.BaseContracts
import com.everis.workshop.ui.base.BasePresenter
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener

/**
 * NO SE USA, PERTENECE A ARQUITECTURA VIPER, ESTÁ PARA VER DIFERENCIAS CON MVVM
 */
class MainPresenter(view: BaseContracts.View?) : BasePresenter(view) {

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        //router = MainRouterImpl(activity)
        interactor = MainInteractor(this)
        (interactor as MainInteractor).init(activity)
    }

    fun buttonAction(objectAction: Object) {
        //(router as MainRouterImpl).goMapView(objectAction as MapCoordinates)
    }

    fun getUserData(onSuccess: (Result) -> Unit, onError: (Throwable?) -> Unit) {
        (interactor as MainInteractor).getDataCallBack(onSuccess, onError)
    }

    fun initCurrentPosicion(location: (Location) -> Unit) {
        (interactor as MainInteractor).initCurrentPosicion(location)
    }

    fun checkLocation(googleApiClient: GoogleApiClient, locationListener: LocationListener) {
        (interactor as MainInteractor).checkLocation(googleApiClient, locationListener)
    }
}