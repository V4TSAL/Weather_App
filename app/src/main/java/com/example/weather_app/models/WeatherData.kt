package com.example.weather_app.models

import com.example.weather_app.models.CurrentConditions
import com.example.weather_app.models.Day

data class WeatherData(
    val address: String,
    val alerts: List<Any>,
    val currentConditions: CurrentConditions,
    val days: List<Day>,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val queryCost: Double,
    val resolvedAddress: String,
    val timezone: String,
    val tzoffset: Double
)