package com.kashif.weathercast

import android.content.Context
import android.location.Geocoder
import android.widget.ImageView
import android.widget.Toast
import coil.load
import com.kashif.weathercast.models.WeatherParcel
import com.kashif.weathercast.models.weatherOneCall.OneCallWeatherResponse
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun getWeatherParcel(
        context: Context,
        response: OneCallWeatherResponse
    ): WeatherParcel {
        return WeatherParcel(
            response,
            getAddressFromGeoCoder(context, response.lat, response.lon)
        )
    }

    fun getAddressFromGeoCoder(context: Context, lat: Double, lon: Double): String {
        return try {
            val gc = Geocoder(context, Locale.ENGLISH)
            val address = gc.getFromLocation(lat, lon, 1)
            address[0].getAddressLine(0)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Could not get accurate location detail.",
                Toast.LENGTH_LONG
            ).show()
            "Unknown place."
        }
    }

    fun loadWeatherIcons(
        iconId: String,
        iv: ImageView
    ) {
        val imagePath = Constants.BASE_IMAGE_PATH + iconId + "@2x.png"
        iv.load(imagePath)
    }

    fun formatTime(time: Int): String {
        val formatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return formatter.format(Date(time * 1000L))
    }

    fun formatDay(time: Int): String {
        val formatter = SimpleDateFormat("d MMM", Locale.getDefault())
        return formatter.format(Date(time * 1000L))
    }

}