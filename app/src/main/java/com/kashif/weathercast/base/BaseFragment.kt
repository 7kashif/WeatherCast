package com.kashif.weathercast.base

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.GoogleMap
import com.kashif.weathercast.R
import com.kashif.weathercast.viewModel.FavoritePlacesViewModel
import com.kashif.weathercast.viewModel.SharedViewModel
import com.kashif.weathercast.viewModel.WeatherViewModel

open class BaseFragment : Fragment() {
    protected lateinit var mMap: GoogleMap
    protected val weatherViewModel: WeatherViewModel by activityViewModels()
    protected val favoritePlacesViewModel: FavoritePlacesViewModel by activityViewModels()
    protected val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.maps_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
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