package com.example.weather_app.weather

import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object CurrentLocationWeather {
    var currentTemperature = mutableDoubleStateOf(0.0)
    var currentWeather = mutableStateOf("Not available")
    var currentLocation = mutableStateOf("Not available")
}