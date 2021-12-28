package com.kashif.weathercast.repository

import com.google.android.gms.maps.model.LatLng
import com.kashif.weathercast.api.WeatherApiService
import com.kashif.weathercast.models.currentWeather.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class WeatherRepository(private val api: WeatherApiService) {

    suspend fun getCurrentWeather(latLng: LatLng): Response<CurrentWeatherResponse>  {
        return withContext(Dispatchers.IO) {
            api.getCurrentWeather(lat = latLng.latitude, lon = latLng.longitude)
        }
    }

}