package com.example.weather_app.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weather_app.models.WeatherData

@Entity
@TypeConverters(Converters::class)
data class WeatherTable(
    @PrimaryKey(autoGenerate = false)
    val location:String,
    val weather:WeatherData
)
