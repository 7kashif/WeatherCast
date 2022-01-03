package com.kashif.weathercast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.kashif.weathercast.base.BaseFragment
import com.kashif.weathercast.R
import com.kashif.weathercast.databinding.HomeFragmentBinding
import com.kashif.weathercast.models.Services
import com.kashif.weathercast.viewModel.FavoritePlacesViewModel
import com.kashif.weathercast.viewModel.WeatherViewModel


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
                mMap.addMarker(
                    MarkerOptions().position(place.latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.png_heart))
                )
            }
        })

        viewModel.weatherResponse.observe(viewLifecycleOwner, {
            when (it) {
                is Services.ResponseSuccess -> {
                    binding.cvLoading.isVisible = false
                    val bundle = Bundle().apply {
                        putParcelable("response", it.response)
                    }
                    findNavController().navigate(
                        R.id.action_homeFragment_to_currentWeatherBottomSheetFragment,
                        bundle
                    )
                }
                else -> {}
            }
        })
    }

    private fun setMapClickListeners(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            map.addMarker(MarkerOptions().position(latLng))
        }
        map.setOnMarkerClickListener {
            binding.cvLoading.isVisible = true
            viewModel.getCurrentWeather(it.position)
            true
        }
    }

}