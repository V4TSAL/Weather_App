package com.example.weather_app

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
data class WeatherTable(
    @PrimaryKey(autoGenerate = false)
    val location:String
)
