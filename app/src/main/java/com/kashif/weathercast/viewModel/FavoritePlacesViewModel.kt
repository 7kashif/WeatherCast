package com.kashif.weathercast.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.kashif.weathercast.repository.FavoritePlacesRepository
import com.kashif.weathercast.room.FavoritePlace
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritePlacesViewModel
@Inject
constructor(
    private val repo: FavoritePlacesRepository
): ViewModel(){
    val favoritePlacesList = repo.getAllFavoritePlaces()

    fun addFavoritePlace(favoritePlace: FavoritePlace) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.addFavoritePlace(favoritePlace)
        }
    }

    fun deleteSingleFavoritePlace(latlng: LatLng) {
        CoroutineScope(Dispatchers.IO).launch {
            repo.deleteSingleFavoritePlace(latlng)
        }
    }

    suspend fun getFavoritePlaceByLatLng(latlng: LatLng): FavoritePlace? =
        withContext(Dispatchers.IO) {
            repo.getFavoritePlaceWithLatLng(latlng)
        }

}

