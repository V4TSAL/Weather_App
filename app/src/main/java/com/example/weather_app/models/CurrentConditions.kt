package com.example.weather_app.models

data class CurrentConditions(
    val cloudcover: Double,
    val conditions: String,
    val datetime: String,
    val datetimeEpoch: Double,
    val dew: Double,
    val feelslike: Double,
    val humidity: Double,
    var icon: String,
    val moonphase: Double,
    val precip: Double,
    val precipprob: Double,
    val preciptype: Any,
    val pressure: Double,
    val severerisk: Double,
    val snow: Double,
    val snowdepth: Double,
    val solarenergy: Double,
    val solarradiation: Double,
    val source: String,
    val stations: List<Any>,
    val sunrise: String,
    val sunriseEpoch: Double,
    val sunset: String,
    val sunsetEpoch: Double,
    val temp: Double,
    val uvindex: Double,
    val visibility: Double,
    val winddir: Double,
    val windgust: Double,
    val windspeed: Double
)