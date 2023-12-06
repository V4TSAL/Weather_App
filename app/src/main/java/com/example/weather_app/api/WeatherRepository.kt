package com.example.weather_app.api

import android.content.Context
import androidx.room.util.query
import androidx.sqlite.db.SimpleSQLiteQuery
import com.example.weather_app.database.WeatherDao
import com.example.weather_app.models.WeatherData
import com.example.weather_app.weather.CurrentLocation
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository(private val context: Context, private val weatherDao: WeatherDao) {
    val baseUrl = "https://weather.visualcrossing.com"
    val retrofitBuilder =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(baseUrl)
            .build().create(ApiInterface::class.java)

    suspend fun getWeather(onResponse:suspend (Boolean,WeatherData)->Unit){
        if (InternetAvailability.isOnline(context = context)) {
            var response=retrofitBuilder.getWeather(city = CurrentLocation.currentLocation)
            if(response.isSuccessful){
                onResponse(true, response.body()!!)
            }
            else{
                onResponse(false, response.body()!!)
            }
        }
        else{
            var query =
                SimpleSQLiteQuery("SELECT * FROM weathertable WHERE location = '${CurrentLocation.currentLocation}'")
            val data =weatherDao.getWeatherForALocation(query).weather
            onResponse(true,data)
        }

    }
}