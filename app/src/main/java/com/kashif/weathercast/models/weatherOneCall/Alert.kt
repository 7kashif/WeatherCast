package com.kashif.weathercast.models.weatherOneCall

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Alert(
    val description: String,
    val end: Int,
    val event: String,
    val sender_name: String,
    val start: Int,
    val tags: List<String>
):Parcelable