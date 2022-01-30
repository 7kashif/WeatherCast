package com.kashif.weathercast.fragments

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.kashif.weathercast.R
import com.kashif.weathercast.Utils
import com.kashif.weathercast.base.BaseFragment
import com.kashif.weathercast.databinding.HomeFragmentBinding
import com.kashif.weathercast.models.Services
import com.kashif.weathercast.models.WeatherParcel
import com.kashif.weathercast.viewModel.FavoritePlacesViewModel
import com.kashif.weathercast.viewModel.WeatherViewModel
import java.util.*


class HomeFragment : BaseFragment(), OnMapReadyCallback {
    private lateinit var binding: HomeFragmentBinding
    private val viewModel: WeatherViewModel by activityViewModels()
    private val favoritePlacesViewModel: FavoritePlacesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager
            .findFragmentById(binding.map.id) as SupportMapFragment
        mapFragment.getMapAsync(this)
        addObservers()
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        val latitude = 33.65953352791636
        val longitude = 73.02403370417115
        val zoomLevel = 10f

        val uniLatLong = LatLng(latitude, longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uniLatLong, zoomLevel))
        mMap.addMarker(MarkerOptions().position(uniLatLong))
        setMapClickListeners(mMap)
    }

    private fun addObservers() {
        favoritePlacesViewModel.favoritePlacesList.observe(viewLifecycleOwner, {
            it.forEach { place ->
                val marker = mMap.addMarker(
                    MarkerOptions().position(place.latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.png_heart))
                )

                marker?.let { mark ->
                    favoritePlacesViewModel.addMarkerToMarkerList(mark)
                }
            }
            favoritePlacesViewModel.checkAndMaintainMarkers(it)
        })

        favoritePlacesViewModel.removeMarker.observe(viewLifecycleOwner, {
            it.remove()
        })

        viewModel.oneCallWeather.observe(viewLifecycleOwner, {
            when (it) {
                is Services.Loading -> {
                    binding.cvLoading.isVisible = true
                }
                is Services.OneCallResponseSuccess -> {
                    binding.cvLoading.isVisible = false
                    Utils.getWeatherParcelBundle(requireContext(),it.response)?.let {bundle->
                        findNavController().navigate(
                            R.id.action_homeFragment_to_currentWeatherBottomSheetFragment,
                            bundle
                        )
                    }
                }
                is Services.ResponseError -> {
                    binding.cvLoading.isVisible = false
                    Toast.makeText(activity,"An error occurred.",Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        })
    }

    private fun setMapClickListeners(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            map.addMarker(MarkerOptions().position(latLng))
            viewModel.getWeatherWithOneCall(latLng)
        }

        map.setOnMarkerClickListener {
            if (favoritePlacesViewModel.isMarkerFavorite(it)) {
                viewModel.getWeatherWithOneCall(LatLng(it.position.latitude,it.position.longitude))
            } else {
                it.remove()
            }
            true
        }
    }

}