package com.kashif.weathercast.models.weatherOneCall

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Minutely(
    val dt: Int,
    val precipitation: Double
):Parcelable