package com.kashif.weathercast.models.currentWeather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
):Parcelable