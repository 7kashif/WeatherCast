package com.kashif.weathercast

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kashif.weathercast.databinding.ActivityMapsBinding
import com.kashif.weathercast.databinding.CurrentWeatherBottomSheetBinding
import com.kashif.weathercast.models.Services
import com.kashif.weathercast.models.currentWeather.CurrentWeatherResponse
import com.kashif.weathercast.viewModel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addObservers()
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(binding.map.id) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latitude = 33.65953352791636
        val longitude = 73.02403370417115
        val zoomLevel = 10f

        val uniLatLong = LatLng(latitude,longitude)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uniLatLong,zoomLevel))
        mMap.addMarker(MarkerOptions().position(uniLatLong))
        setMapClickListeners(mMap)
    }

    private fun addObservers() {
        viewModel.weatherResponse.observe(this, {
            when(it) {
                is Services.ResponseSuccess -> {
                    showBottomSheet(it.response)
                }
                else -> {}
            }
        })
    }

    private fun showBottomSheet(response: CurrentWeatherResponse) {
        val sheetBinding = CurrentWeatherBottomSheetBinding.inflate(layoutInflater)

        val bottomSheet = BottomSheetDialog(this, R.style.bottomSheetStyle)
        bottomSheet.apply {
            setContentView(sheetBinding.root)
            setCancelable(true)
            setCanceledOnTouchOutside(true)
        }

        bottomSheet.setOnShowListener {
            Toast.makeText(this,"Bottom sheet is visible now.",Toast.LENGTH_LONG).show()
        }

        sheetBinding.apply {
            tvLat.text = getString(R.string.set_lat,response.coord.lat.toString())
            tvLng.text = getString(R.string.set_lng,response.coord.lon.toString())
            tvCityName.text = response.name
            tvDescription.text = response.weather[0].description
            tvTemperature.text = getString(R.string.set_temperature,response.main.temp.toString())
            tvMinTemperature.text = getString(R.string.set_min_temperature,response.main.temp_min.toString())
            tvMaxTemperature.text = getString(R.string.set_max_temperature,response.main.temp_max.toString())
            tvWindSpeed.text = getString(R.string.set_wind_speed,response.wind.speed.toString())
            tvPressure.text = getString(R.string.set_pressure,response.main.pressure.toString())
            tvHumidity.text = getString(R.string.set_humidity,response.main.humidity.toString())
            tvClouds.text = getString(R.string.set_clouds,response.clouds.all.toString())
        }

        bottomSheet.show()
    }

    private fun setMapClickListeners(map: GoogleMap) {
        map.setOnMapLongClickListener {latLng->
            val snippet = String.format(
                Locale.getDefault(),
                "Lat: %1$.5f , Long: %2$.5f",
                latLng.latitude,
                latLng.longitude
            )
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(getString(R.string.dropped_pin))
                    .snippet(snippet)
            )
        }
        map.setOnInfoWindowLongClickListener {
            it.remove()
        }
        map.setOnMarkerClickListener {
            viewModel.getCurrentWeather(it.position)
            true
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.maps_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.normal_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            true
        }
        R.id.hybrid_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            true
        }
        R.id.satellite_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            true
        }
        R.id.terrain_map -> {
            mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}