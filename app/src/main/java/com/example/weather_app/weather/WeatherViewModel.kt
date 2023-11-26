package com.example.composepractise.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.api.ApiInterface
import com.example.weather_app.models.WeatherData
import com.example.weather_app.weather.CurrentLocation
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {
    val baseUrl = "https://weather.visualcrossing.com"
    val liveApiData = MutableLiveData<ArrayList<WeatherData>>()
    val tempArray = ArrayList<WeatherData>()
    fun getWeatherFromApi(onResponse:suspend (Boolean)->Unit) {
        val retrofitBuilder =
            Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl)
                .build().create(ApiInterface::class.java)
        viewModelScope.launch {
            val weather = retrofitBuilder.getWeather(city = CurrentLocation.currentLocation)
            if(weather.isSuccessful){
                tempArray.clear()
                tempArray.add(weather.body()!!)
                liveApiData.value = tempArray
                onResponse(true)
            }
            else{
                onResponse(false)
                Log.d("ERROR CHECK", "getWeatherFromApi: ${weather.code()}")
            }
        }
    }
}