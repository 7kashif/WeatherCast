package com.kashif.weathercast.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.google.android.gms.maps.model.LatLng

@Dao
interface FavoritePlacesDao {
    @Insert
    suspend fun addFavoritePlace(favoritePlace: FavoritePlace)

    @Query("DELETE from favoritePlaces")
    fun deleteAllFavoritePlaces()

    @Query("SELECT * from favoritePlaces ORDER BY primaryKey DESC")
    fun getAllFavoritePlaces(): LiveData<List<FavoritePlace>>

    @Query("SELECT * FROM favoritePlaces WHERE latLng=:latlng")
    suspend fun getFavoritePlaceByLatLng(latlng:LatLng): FavoritePlace?

    @Query("DELETE FROM favoritePlaces WHERE latLng=:latlng")
    suspend fun deleteSingleFavoritePlace(latlng: LatLng)

}