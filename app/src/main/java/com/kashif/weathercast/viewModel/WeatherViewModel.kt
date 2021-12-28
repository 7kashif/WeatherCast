package com.kashif.weathercast.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.kashif.weathercast.Constants.TAG
import com.kashif.weathercast.api.WeatherApiService
import com.kashif.weathercast.models.Services
import com.kashif.weathercast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
@Inject
constructor(
    weatherApiService: WeatherApiService
): ViewModel(){
    private var repo: WeatherRepository = WeatherRepository(weatherApiService)
    private val _weatherResponse = MutableLiveData<Services>()
    val weatherResponse: LiveData<Services> get() = _weatherResponse


    fun getCurrentWeather(latLng: LatLng) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repo.getCurrentWeather(latLng)
                _weatherResponse.postValue(Services.ResponseSuccess(response.body()!!))
            } catch (e: HttpException) {
                Log.e(TAG,e.message())
            } catch (e: Exception) {
                Log.e(TAG,e.message!!)
            }
        }
    }

}