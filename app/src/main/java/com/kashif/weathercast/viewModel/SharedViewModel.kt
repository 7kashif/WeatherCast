package com.kashif.weathercast.viewModel

import androidx.lifecycle.ViewModel
import com.kashif.weathercast.models.WeatherParcel

class SharedViewModel: ViewModel() {
    lateinit var currentlyDisplayedWeatherResponse: WeatherParcel
}