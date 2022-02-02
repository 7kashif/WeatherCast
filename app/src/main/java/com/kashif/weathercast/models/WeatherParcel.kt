package com.kashif.weathercast.models

import com.kashif.weathercast.models.weatherOneCall.OneCallWeatherResponse

data class WeatherParcel(
    var response: OneCallWeatherResponse,
    var address: String,
)
