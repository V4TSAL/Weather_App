package com.example.weather_app

import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface WeatherDao {
    @Upsert
      suspend fun insertWeather(weatherTable: WeatherTable)
    @Query("SELECT * FROM weathertable")
     suspend fun getAllWeatherData(): List<WeatherTable>
}