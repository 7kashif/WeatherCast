package com.kashif.weathercast.models

import com.google.android.gms.maps.model.Marker
import com.kashif.weathercast.models.weatherOneCall.OneCallWeatherResponse

sealed class Services {
    object Loading : Services()
    object Idle: Services()
    data class OneCallResponseSuccess(val response: OneCallWeatherResponse) : Services()
    data class ResponseError(val error: String) : Services()
}
