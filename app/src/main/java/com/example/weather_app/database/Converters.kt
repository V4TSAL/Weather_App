package com.example.weather_app.database

import androidx.room.TypeConverter
import com.example.weather_app.models.CurrentConditions
import com.example.weather_app.models.Day
import com.example.weather_app.models.Hour
import com.example.weather_app.models.WeatherData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromCurrentConditions(currentConditions: CurrentConditions): String {
        return Gson().toJson(currentConditions)
    }

    @TypeConverter
    fun toCurrentConditions(data: String): CurrentConditions {
        val type = object : TypeToken<CurrentConditions>() {}.type
        return Gson().fromJson(data, type)
    }

    @TypeConverter
    fun fromDay(day: Day): String {
        return Gson().toJson(day)
    }

    @TypeConverter
    fun toDay(data: String): Day {
        val type = object : TypeToken<Day>() {}.type
        return Gson().fromJson(data, type)
    }

    @TypeConverter
    fun fromHour(hour: Hour): String {
        return Gson().toJson(hour)
    }

    @TypeConverter
    fun toHour(data: String): Hour {
        val type = object : TypeToken<Hour>() {}.type
        return Gson().fromJson(data, type)
    }

    @TypeConverter
    fun fromWeatherData(weatherData: WeatherData): String {
        return Gson().toJson(weatherData)
    }

    @TypeConverter
    fun toWeatherData(data: String): WeatherData {
        val type = object : TypeToken<WeatherData>() {}.type
        return Gson().fromJson(data, type)
    }
}