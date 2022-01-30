package com.kashif.weathercast.models.weatherOneCall

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
):Parcelable