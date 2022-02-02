package com.kashif.weathercast.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.kashif.weathercast.repository.FavoritePlacesRepository
import com.kashif.weathercast.room.FavoritePlace
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritePlacesViewModel
@Inject
constructor(
    private val repo: FavoritePlacesRepository
) : ViewModel() {
    val favoritePlacesList = repo.getAllFavoritePlaces()
    private val _eventFlag = MutableLiveData<Boolean>()
    val eventFlag: LiveData<Boolean> get() = _eventFlag

    fun addFavoritePlace(favoritePlace: FavoritePlace) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addFavoritePlace(favoritePlace)
        }.invokeOnCompletion {
            modifyEventFlag(true)
        }
    }

    fun modifyEventFlag(flag: Boolean) {
        _eventFlag.postValue(flag)
    }

    fun deleteSingleFavoritePlace(latlng: LatLng) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteSingleFavoritePlace(latlng)
        }.invokeOnCompletion {
            modifyEventFlag(true)
        }
    }

    suspend fun getFavoritePlaceByLatLng(latlng: LatLng): FavoritePlace? =
        withContext(Dispatchers.IO) {
            repo.getFavoritePlaceWithLatLng(latlng)
        }

}

