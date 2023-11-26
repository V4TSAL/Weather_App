package com.example.weather_app.navgraph

sealed class Screen(val route: String){
    data object WeatherApp: Screen(route = "weather_app")
    data object ChooseLocation: Screen(route = "choose_location")
}
