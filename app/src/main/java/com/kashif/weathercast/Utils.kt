package com.kashif.weathercast

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.kashif.weathercast.models.WeatherParcel
import com.kashif.weathercast.models.weatherOneCall.OneCallWeatherResponse
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getWeatherParcelBundle(
        context: Context,
        response: OneCallWeatherResponse
    ): Bundle? {
        return try {
            val gc = Geocoder(context, Locale.ENGLISH)
            val address = gc.getFromLocation(response.lat, response.lon, 1)
            val parcel = WeatherParcel(response, address[0].getAddressLine(0))
            Bundle().apply {
                putParcelable("response", parcel)
            }
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "An error occurred. \n Try switching aeroplane mode.",
                Toast.LENGTH_LONG
            ).show()
            null
        }
    }

    fun loadWeatherIcons(
        context: Context,
        iconId: String,
        iv: ImageView
    ) {
        Glide.with(context)
            .load(context.getString(R.string.weather_icon_url, iconId))
            .into(iv)
    }

    fun formatTime(time: Int): String {
        val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return formatter.format(Date(time*1000L))
    }

}