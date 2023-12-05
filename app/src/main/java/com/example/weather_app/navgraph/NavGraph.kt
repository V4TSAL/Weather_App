package com.example.composepractise.navgraph

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.weather_app.weather.WeatherUi
import com.example.composepractise.weather.WeatherViewModel
import com.example.weather_app.WeatherDao
import com.example.weather_app.navgraph.Screen
import com.example.weather_app.weather.AllLocations
import com.example.weather_app.weather.ChooseLocation
import com.example.weather_app.weather.CurrentLocation

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    viewModel: WeatherViewModel,
    sharedPreferences: SharedPreferences,
    weatherDao: WeatherDao
) {
    var startDestination = Screen.ChooseLocation.route
    var currentLocation = sharedPreferences.getString("CURRENT_LOCATION", "")
    var allLocations=sharedPreferences.getStringSet("ALL_LOCATIONS", mutableSetOf<String>())
    var temp = sharedPreferences.getStringSet(
        "ALL_LOCATIONS",
        mutableSetOf<String>()
    )
    Log.d("KJSDGIJSDIG", "ChooseLocation: ${temp}")
    if (!currentLocation.equals("")) {
        startDestination = Screen.WeatherApp.route
        CurrentLocation.currentLocation = currentLocation!!
        viewModel.getWeatherFromApi {}
    }
    NavHost(navController = navController, startDestination = startDestination) {
        composable(
            route = Screen.WeatherApp.route
        ) {
            WeatherUi(viewModel = viewModel,navController=navController)
        }
        composable(
            route = Screen.ChooseLocation.route
        ) {
            ChooseLocation(
                navController = navController,
                viewModel = viewModel,
                sharedPreferences = sharedPreferences,
                weatherDao=weatherDao
            )
        }
        composable(
            route=Screen.AllLocations.route
        ){
            AllLocations(viewModel=viewModel,sharedPreferences=sharedPreferences,navController=navController,weatherDao=weatherDao)
        }
    }
}