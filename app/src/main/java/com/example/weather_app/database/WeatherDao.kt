package com.example.weather_app.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Upsert
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface WeatherDao {
    @Upsert
      suspend fun insertWeather(weatherTable: WeatherTable)
    @Query("SELECT * FROM weathertable")
     suspend fun getAllWeatherData(): List<WeatherTable>
     @RawQuery
     suspend fun getWeatherForALocation(sqLiteQuery: SupportSQLiteQuery):WeatherTable
}