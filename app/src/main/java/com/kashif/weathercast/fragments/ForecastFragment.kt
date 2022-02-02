package com.kashif.weathercast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.model.LatLng
import com.kashif.weathercast.Constants
import com.kashif.weathercast.R
import com.kashif.weathercast.Utils
import com.kashif.weathercast.adapter.DailyAdapter
import com.kashif.weathercast.adapter.HourlyAdapter
import com.kashif.weathercast.base.BaseFragment
import com.kashif.weathercast.databinding.ForecastFragmentBinding
import com.kashif.weathercast.models.WeatherParcel
import com.kashif.weathercast.room.FavoritePlace
import kotlinx.coroutines.launch

class ForecastFragment : BaseFragment() {
    private lateinit var binding: ForecastFragmentBinding
    private val hourlyAdapter = HourlyAdapter()
    private val dailyAdapter = DailyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ForecastFragmentBinding.inflate(inflater)
        val response = sharedViewModel.currentlyDisplayedWeatherResponse
        setUpViews(response)

        return binding.root
    }

    private fun setUpViews(resp: WeatherParcel) {
        binding.apply {
            val latLng = LatLng(
                resp.response.lat,
                resp.response.lon
            )
            response = resp
            constant = Constants
            tvSunrise.text = getString(
                R.string.set_sunrise,
                Utils.formatTime(resp.response.current.sunrise)
            )
            tvSunset.text = getString(
                R.string.set_sunset,
                Utils.formatTime(resp.response.current.sunset)
            )
            rvHourly.adapter = hourlyAdapter
            rvDaily.adapter = dailyAdapter
            hourlyAdapter.submitList(resp.response.hourly)
            dailyAdapter.submitList(resp.response.daily)

            Utils.loadWeatherIcons(
                resp.response.current.weather[0].icon,
                weatherIcon
            )

            lifecycleScope.launch {
                if (favoritePlacesViewModel.getFavoritePlaceByLatLng(latLng) != null)
                    binding.cbAddFavorite.isChecked = true
            }

            favoritePlacesViewModel.eventFlag.observe(viewLifecycleOwner,{
                if(it)
                    findNavController().popBackStack()
            })

            binding.cbAddFavorite.setOnCheckedChangeListener { button, isChecked ->
                if (button.isPressed) {
                    if (isChecked) {
                        val place = FavoritePlace(latLng = latLng)
                        favoritePlacesViewModel.addFavoritePlace(place)
                        Toast.makeText(activity, "Favorite place added.", Toast.LENGTH_LONG).show()
                    } else {
                        favoritePlacesViewModel.deleteSingleFavoritePlace(latLng)
                        Toast.makeText(activity, "Favorite place removed.", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        weatherViewModel.idleService()
    }

}