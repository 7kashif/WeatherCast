package com.kashif.weathercast.models.weatherOneCall

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class OneCallWeatherResponse(
    val alerts: List<Alert>,
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>,
    val timezone: String,
    val timezone_offset: Int
):Parcelable