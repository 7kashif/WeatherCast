package com.kashif.weathercast.models

import android.os.Parcelable
import com.kashif.weathercast.models.weatherOneCall.OneCallWeatherResponse
import kotlinx.android.parcel.Parcelize

@Parcelize
data class WeatherParcel(
    var response: OneCallWeatherResponse,
    var address: String
):Parcelable
