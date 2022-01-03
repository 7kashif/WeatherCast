package com.kashif.weathercast.room

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject

class LatLngTypeConverter {
    @TypeConverter
    fun fromLatLng(latLng: LatLng): String =
        JSONObject().apply {
            put("latitude", latLng.latitude)
            put("longitude", latLng.longitude)
        }.toString()

    @TypeConverter
    fun toLatLng(latlng: String): LatLng {
        val json = JSONObject(latlng)
        return LatLng(json.getDouble("latitude"), json.getDouble("longitude"))
    }

}