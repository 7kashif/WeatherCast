package com.kashif.weathercast.models.currentWeather

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Clouds(
    val all: Int
):Parcelable