package com.kashif.weathercast.repository

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.kashif.weathercast.room.FavoritePlace
import com.kashif.weathercast.room.FavoritePlacesDao

class FavoritePlacesRepository(private val database: FavoritePlacesDao) {

    fun getAllFavoritePlaces(): LiveData<List<FavoritePlace>> =
        database.getAllFavoritePlaces()

    fun deleteAllFavoritePlaces() {
        database.deleteAllFavoritePlaces()
    }

    suspend fun deleteSingleFavoritePlace(latlng: LatLng) {
        database.deleteSingleFavoritePlace(latlng)
    }

    suspend fun addFavoritePlace(favoritePlace: FavoritePlace) {
        database.addFavoritePlace(favoritePlace)
    }

    suspend fun getFavoritePlaceWithLatLng(latLng: LatLng): FavoritePlace? =
        database.getFavoritePlaceByLatLng(latLng)


}