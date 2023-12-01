package com.example.weather_app.api

import com.example.weather_app.models.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @GET("/VisualCrossingWebServices/rest/services/timeline/{city}")
    suspend fun getWeather(
        @Path("city") city: String,
        @Query("unitGroup") unitGroup: String = "us",
        @Query("key") apiKey: String = "UQA6NEXJLLDYNMRP6B777L4CA",
        @Query("contentType") contentType: String = "json"
    ): Response<WeatherData>
}