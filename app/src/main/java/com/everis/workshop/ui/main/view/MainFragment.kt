package com.everis.workshop.ui.main.view

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.everis.workshop.ui.base.BaseFragment
import com.everis.workshop.R
import com.everis.workshop.data.model.map.MapCoordinates
import com.everis.workshop.data.network.entities.WsRequestUser
import com.everis.workshop.data.network.interfaces.ApiService
import com.everis.workshop.data.network.model.User
import com.everis.workshop.data.repository.UserRepositoryImpl
import com.everis.workshop.ui.main.viewmodel.MainViewModelImpl
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainFragment : MainView, BaseFragment(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    LocationListener {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private lateinit var googleApiClient: GoogleApiClient
    private var location = Location("0,0")

    private val SEPARATOR = ":"
    private lateinit var user: User

    private lateinit var viewModel: MainViewModelImpl

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_main, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, MainViewModelImpl.FACTORY(
            UserRepositoryImpl(
                WsRequestUser(
                    ApiService.create())
            )
        )).get(MainViewModelImpl::class.java)

      //  viewModel = getViewModel()

        activity?.let {activity ->
            googleApiClient = GoogleApiClient.Builder(activity)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()

            viewModel.checkLocation(activity)
        }
        setupObservers()
        getInfoView()
    }

    fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner, Observer {response ->
            closeLoadingDialog()
            user = response
            renderInfo()
            addSeparators()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {message ->
            closeLoadingDialog()
            showErrorScreen(message)
        })

        viewModel.location.observe(viewLifecycleOwner, Observer {
            this.location = it
        } )

        viewModel.permissionGranted.observe(viewLifecycleOwner, Observer {permission ->
            when (permission) {
                true -> {
                    //TODO action granted
                }
                false -> {
                    //TODO action denied
                }
            }
        })

        viewModel.locationEnabled.observe(viewLifecycleOwner, Observer { locationEnable ->
            when (locationEnable) {
                true -> {
                    //TODO action enabled
                }
                false -> {
                    //TODO action disabled
                }
            }
        })
    }


    override fun onStart() {
        super.onStart()
        setViewActions()
        googleApiClient.connect()

    }

    override fun onStop() {
        super.onStop();
        if (googleApiClient.isConnected) {
            googleApiClient.disconnect()
        }
    }

    private fun setViewActions() {
        buttonMap.setOnClickListener {
            goMap(
                lat = user.location.coordinates.latitude.toDouble(),
                long = user.location.coordinates.longitude.toDouble()
            )
        }

        buttonMapMyPosition.setOnClickListener {
            goMap(location.latitude, location.longitude)
        }
    }

    private fun goMap(lat: Double, long: Double) {
        var mapCoord = MapCoordinates()
        mapCoord.latitude = lat
        mapCoord.longitude = long
        activity?.let {
            viewModel.buttonAction(it, mapCoord as Object)
        }

    }

    private fun getInfoView() {
        showLoadingDialog()
        viewModel.getUserData()
    }

    private fun renderInfo() {
        tvGenderData.text = user.gender
        tvNameData.text = user.name.toString()
        tvLocationAdressData.text = user.location.getAdress()
        tvLocationCoorData.text = user.location.coordinates.toString()
        tvLocationTimeZoneData.text = user.location.timezone.toString()
    }

    private fun addSeparators() {
        tvGender.text = tvGender.text.toString() + SEPARATOR
        tvName.text = tvName.text.toString() + SEPARATOR
        tvEmail.text = tvEmail.text.toString() + SEPARATOR
        tvLocationAdress.text = tvLocationAdress.text.toString() + SEPARATOR
        tvLocationCoor.text = tvLocationCoor.text.toString() + SEPARATOR
        tvLocationTimeZone.text = tvLocationTimeZone.text.toString() + SEPARATOR
    }

    override fun onConnected(bundle: Bundle?) {
        activity?.let {activity ->
            showLoadingDialog()
            viewModel.initCurrentPosition(activity = activity)
        }
    }

    override fun onConnectionSuspended(p0: Int) {
        googleApiClient.connect()
    }

    override fun onConnectionFailed(conectionResult: ConnectionResult) {
        closeLoadingDialog()
        //TODO do something when fail the connection
    }

    override fun onLocationChanged(location: Location?) {
        //TODO action to when de location changed

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}