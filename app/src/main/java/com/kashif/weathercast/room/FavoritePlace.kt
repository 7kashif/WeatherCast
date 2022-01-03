package com.kashif.weathercast.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng

@Entity(tableName = "favoritePlaces")
data class FavoritePlace(
    @PrimaryKey(autoGenerate = true)
    var primaryKey: Long = 0L,
    var latLng: LatLng
)
