package com.kashif.weathercast.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
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
    private val repo: WeatherRepository
) : ViewModel() {
    private val _oneCallWeather = MutableLiveData<Services>()
    val oneCallWeather: LiveData<Services> get() = _oneCallWeather

    fun getWeatherWithOneCall(latLng: LatLng) {
        CoroutineScope(Dispatchers.IO).launch {
            _oneCallWeather.postValue(Services.Loading)
            try {
                val response = repo.getWeatherWithOneCall(latLng)
                _oneCallWeather.postValue(Services.OneCallResponseSuccess(response.body()!!))
            } catch (e: HttpException) {
                _oneCallWeather.postValue(Services.ResponseError(e.message()))
            } catch (e: Exception) {
                _oneCallWeather.postValue(Services.ResponseError(e.message!!))
            }
        }
    }

}