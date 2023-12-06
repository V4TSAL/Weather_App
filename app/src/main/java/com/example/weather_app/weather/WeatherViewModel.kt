package com.example.composepractise.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.weather_app.api.ApiInterface
import com.example.weather_app.api.InternetAvailability
import com.example.weather_app.api.WeatherRepository
import com.example.weather_app.models.WeatherData
import com.example.weather_app.weather.CurrentLocation
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel(private val weatherRepository: WeatherRepository) : ViewModel() {
    val liveApiData = MutableLiveData<ArrayList<WeatherData>>()
    private val tempArray = ArrayList<WeatherData>()
    fun getWeatherFromApi(onResponse: suspend (Boolean) -> Unit) {
        viewModelScope.launch {
            weatherRepository.getWeather { a, b ->
                if (a) {
                    tempArray.clear()
                    tempArray.add(b)
                    liveApiData.value = tempArray
                    onResponse(true)
                } else {
                    onResponse(false)
                }
            }
        }
    }
}
