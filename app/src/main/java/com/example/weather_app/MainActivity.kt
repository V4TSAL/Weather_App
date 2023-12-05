package com.example.weather_app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.composepractise.navgraph.SetUpNavGraph
import com.example.composepractise.weather.WeatherViewModel
import com.example.weather_app.ui.theme.Weather_AppTheme

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences=getSharedPreferences("location", Context.MODE_PRIVATE)
        val viewModel:WeatherViewModel by viewModels()
        val db= Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java,"weather-database"
        ).build()
        val weatherDao=db.dao()
        setContent {
            Weather_AppTheme {
                    navController= rememberNavController()
                    SetUpNavGraph(navController = navController, viewModel = viewModel,sharedPreferences=sharedPreferences,weatherDao=weatherDao)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Weather_AppTheme {
        Greeting("Android")
    }
}