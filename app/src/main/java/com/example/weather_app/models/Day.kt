package com.example.weather_app.models

data class Day(
    val cloudcover: Double,
    val conditions: String,
    val datetime: String,
    val datetimeEpoch: Double,
    val datetimeInstance: String,
    val description: String,
    val dew: Double,
    val feelslike: Double,
    val feelslikemax: Double,
    val feelslikemin: Double,
    val hours: List<Hour>,
    val humidity: Double,
    val icon: String,
    val moonphase: Double,
    val precip: Double,
    val precipcover: Double,
    val precipprob: Double,
    val precipsum: Double,
    val precipsumnormal: Double,
    val preciptype: Any,
    val pressure: Double,
    val severerisk: Double,
    val snow: Double,
    val snowdepth: Double,
    val snowsum: Double,
    val solarenergy: Double,
    val solarradiation: Double,
    val source: String,
    val stations: List<String>,
    val sunrise: String,
    val sunriseEpoch: Double,
    val sunset: String,
    val sunsetEpoch: Double,
    val temp: Double,
    val tempmax: Double,
    val tempmin: Double,
    val uvindex: Double,
    val visibility: Double,
    val winddir: Double,
    val windgust: Double,
    val windspeed: Double
)