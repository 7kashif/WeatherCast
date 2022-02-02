package com.kashif.weathercast.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kashif.weathercast.Constants
import com.kashif.weathercast.R
import com.kashif.weathercast.Utils
import com.kashif.weathercast.databinding.CurrentWeatherBottomSheetBinding
import com.kashif.weathercast.models.WeatherParcel
import com.kashif.weathercast.viewModel.SharedViewModel

class CurrentWeatherBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: CurrentWeatherBottomSheetBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CurrentWeatherBottomSheetBinding.inflate(inflater)
        val response = sharedViewModel.currentlyDisplayedWeatherResponse
        binding.response = response
        binding.constant = Constants
        setUpViews(response)

        return binding.root
    }

    private fun setUpViews(response: WeatherParcel) {
        Utils.loadWeatherIcons(
            response.response.current.weather[0].icon,
            binding.weatherIcon
        )

        binding.btnSeeForecast.setOnClickListener {
            findNavController().navigate(CurrentWeatherBottomSheetFragmentDirections
                .actionCurrentWeatherBottomSheetFragmentToForecastFragment())
        }

    }

}