package com.kashif.weathercast.models

import com.kashif.weathercast.models.currentWeather.CurrentWeatherResponse

sealed class Services {
    object Loading: Services()
    object Idle: Services()
    data class ResponseSuccess(val response: CurrentWeatherResponse): Services()
    data class ResponseError(val error: String): Services()
}
