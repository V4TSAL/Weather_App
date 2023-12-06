package com.example.weather_app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.composepractise.navgraph.SetUpNavGraph
import com.example.composepractise.weather.WeatherViewModel
import com.example.weather_app.api.WeatherRepository
import com.example.weather_app.database.WeatherDatabase
import com.example.weather_app.ui.theme.Weather_AppTheme

class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences=getSharedPreferences("location", Context.MODE_PRIVATE)
        val db= Room.databaseBuilder(
            applicationContext,
            WeatherDatabase::class.java,"weather-database"
        ).build()
        val weatherDao=db.dao()
        val weatherRepository = WeatherRepository(applicationContext,weatherDao) // Create or inject your WeatherRepository instance here
        val viewModel = WeatherViewModel(weatherRepository)
//        val viewModel = ViewModelProvider(this, viewModelFactory)[WeatherViewModel::class.java]

        setContent {
            Weather_AppTheme {
                    navController= rememberNavController()
                    SetUpNavGraph(navController = navController, viewModel = viewModel,sharedPreferences=sharedPreferences,weatherDao=weatherDao,context=applicationContext)
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