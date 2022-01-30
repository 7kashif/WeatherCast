package com.kashif.weathercast.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
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
    private val markerList = ArrayList<Marker>()
    private val _removeMarker= MutableLiveData<Marker>()
    val removeMarker: LiveData<Marker> get() = _removeMarker

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

    fun checkAndMaintainMarkers(list: List<FavoritePlace>) {
        for(i in markerList.indices) {
            var found = false
            val marker = markerList[i]
            val latLng = LatLng(marker.position.latitude,marker.position.longitude)
            list.forEach { place->
                Log.e("placesViewModel","Inside the foreach loop of places.")
                if(latLng == place.latLng)
                    found = true
            }
            if(!found) {
                _removeMarker.value = marker
                markerList.removeAt(i)
                break
            }
        }
    }

    fun addMarkerToMarkerList(mark: Marker) {
        markerList.add(mark)
    }

    fun isMarkerFavorite(marker: Marker): Boolean = markerList.contains(marker)

}

