package com.kashif.weathercast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.kashif.weathercast.Constants
import com.kashif.weathercast.R
import com.kashif.weathercast.Utils
import com.kashif.weathercast.adapter.DailyAdapter
import com.kashif.weathercast.adapter.HourlyAdapter
import com.kashif.weathercast.databinding.ForecastFragmentBinding

class ForecastFragment : Fragment() {
    private lateinit var binding: ForecastFragmentBinding
    private val args: ForecastFragmentArgs by navArgs()
    private val hourlyAdapter = HourlyAdapter()
    private val dailyAdapter = DailyAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ForecastFragmentBinding.inflate(inflater)
        setUpViews()

        return binding.root
    }

    private fun setUpViews() {
        binding.apply {
            response = args.response
            constant = Constants
            tvSunrise.text = getString(
                R.string.set_sunrise,
                Utils.formatTime(args.response.response.current.sunrise)
            )
            tvSunset.text = getString(
                R.string.set_sunset,
                Utils.formatTime(args.response.response.current.sunset)
            )
            rvHourly.adapter = hourlyAdapter
            rvDaily.adapter = dailyAdapter
            hourlyAdapter.submitList(args.response.response.hourly)
            dailyAdapter.submitList(args.response.response.daily)

            Utils.loadWeatherIcons(
                args.response.response.current.weather[0].icon,
                weatherIcon
            )
        }
    }

}