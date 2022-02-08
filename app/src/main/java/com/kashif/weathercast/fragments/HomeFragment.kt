package com.kashif.weathercast.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment(), OnMapReadyCallback {
    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater)
        addObservers()
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val mapFragment = childFragmentManager
            .findFragmentById(binding.map.id) as SupportMapFragment
        mapFragment.getMapAsync(this)
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
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.png_heart))
                )
            }
            Log.e("favoritePlacesViewModel", it.size.toString())
        })

        weatherViewModel.oneCallWeather.observe(viewLifecycleOwner, {
            when (it) {
                is Services.Loading -> {
                    binding.cvLoading.isVisible = true
                }
                is Services.OneCallResponseSuccess -> {
                    binding.cvLoading.isVisible = false
                    sharedViewModel.currentlyDisplayedWeatherResponse =
                        Utils.getWeatherParcel(requireContext(), it.response)
                    this.findNavController()
                        .navigate(
                            HomeFragmentDirections.actionHomeFragmentToCurrentWeatherBottomSheetFragment()
                        )
                }

                is Services.ResponseError -> {
                    binding.cvLoading.isVisible = false
                    Toast.makeText(activity, "An error occurred.", Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        })
    }

    private fun setMapClickListeners(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            map.addMarker(
                MarkerOptions().position(latLng)
            )?.let {
                weatherViewModel.getWeatherWithOneCall(latLng)
            }
        }

        map.setOnMarkerClickListener {
            lifecycleScope.launch {
                if (favoritePlacesViewModel.getFavoritePlaceByLatLng(
                        LatLng(
                            it.position.latitude,
                            it.position.longitude
                        )
                    ) != null
                ) {
                    weatherViewModel.getWeatherWithOneCall(
                        LatLng(
                            it.position.latitude,
                            it.position.longitude
                        )
                    )
                } else
                    it.remove()
            }
            true
        }
    }

}