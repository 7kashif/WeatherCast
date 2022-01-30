package com.kashif.weathercast.repository

import com.google.android.gms.maps.model.LatLng
import com.kashif.weathercast.api.WeatherApiService
import com.kashif.weathercast.models.weatherOneCall.OneCallWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class WeatherRepository(private val api: WeatherApiService) {
    suspend fun getWeatherWithOneCall(latLng: LatLng): Response<OneCallWeatherResponse> =
        withContext(Dispatchers.IO) {
            api.getWeatherWithOneCall(latLng.latitude, latLng.longitude)
        }

}