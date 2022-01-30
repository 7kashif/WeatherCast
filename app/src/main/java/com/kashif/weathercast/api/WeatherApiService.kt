package com.kashif.weathercast.api

import com.kashif.weathercast.Constants
import com.kashif.weathercast.models.weatherOneCall.OneCallWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET(Constants.END_URL_ONECALL)
    suspend fun getWeatherWithOneCall(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = Constants.API_KEY,
        @Query("units") units: String = "metric"
    ): Response<OneCallWeatherResponse>
}