package com.example.composepractise.navgraph

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import com.example.weather_app.weather.WeatherUi
import com.example.composepractise.weather.WeatherViewModel
import com.example.weather_app.navgraph.Screen
import com.example.weather_app.weather.ChooseLocation

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    viewModel: WeatherViewModel
) {
    NavHost(navController = navController, startDestination = Screen.ChooseLocation.route) {
        composable(
            route= Screen.WeatherApp.route
        ){
            WeatherUi(viewModel = viewModel)
        }
        composable(
            route= Screen.ChooseLocation.route
        ){
            ChooseLocation(navController=navController,viewModel=viewModel)
        }
    }
}