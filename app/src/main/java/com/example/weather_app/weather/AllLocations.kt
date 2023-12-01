package com.example.weather_app.weather

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.composepractise.weather.WeatherViewModel
import com.example.weather_app.R
import com.example.weather_app.fonts.Fonts
import com.example.weather_app.navgraph.Screen

@Composable
fun AllLocations(
    viewModel: WeatherViewModel,
    sharedPreferences: SharedPreferences,
    navController: NavController
) {
    var listOfLocations = sharedPreferences.getStringSet("ALL_LOCATIONS", mutableSetOf<String>())!!.toList()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black.copy(alpha = 0.8f))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }) {
                Icon(imageVector = Icons.Default.ArrowBack, "", tint = Color.White)
            }
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(listOfLocations.size) {
                    LocationItem(location = listOfLocations[it], callback = {
                        it.liveApiData.observeForever {
                            viewModel.liveApiData.value = it
                        }
                        navController.popBackStack()
                    })
                }
            }
        }
    }
}

@Composable
fun LocationItem(location: String, callback: (viewModel: WeatherViewModel) -> Unit) {
    CurrentLocation.currentLocation = location
    val viewModel = WeatherViewModel()
//    viewModel.getWeatherFromApi {}
    val weatherData = viewModel.liveApiData.observeAsState()
    var currentConditions = R.drawable.stars
    if (!weatherData.value.isNullOrEmpty()) {
        currentConditions = getGifForCurrentWeather(weatherData.value!![0].currentConditions.icon)
    }
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = Color.Black)
            .clickable {
                callback(viewModel)
            }
            .paint(
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(
                    ImageRequest
                        .Builder(context)
                        .data(data = currentConditions)
                        .apply(block = {
                            size(Size.ORIGINAL)
                        })
                        .build(), imageLoader = imageLoader
                ),
                colorFilter = ColorFilter.colorMatrix(
                    ColorMatrix().apply { setToScale(0.5f, 0.5f, 0.5f, 1f) })
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = location,
            fontSize = 20.sp,
            fontFamily = Fonts.boldFontFamily,
            color = Color.White,
            textAlign = TextAlign.Center,
        )
    }
}

fun getGifForCurrentWeather(iconInformation: String): Int {
    return if (iconInformation.contains("day")) {
        if (iconInformation.contains("clear")) {
            R.drawable.sunny_beach
        } else if (iconInformation.contains("cloudy")) {
            R.drawable.cloudy
        } else if (iconInformation.contains("snow")) {
            R.drawable.snow_branch
        } else if (iconInformation.contains("rainy")) {
            R.drawable.rain_drops
        } else {
            R.drawable.sunny_beach
        }
    } else {
        if (iconInformation.contains("clear")) {
            R.drawable.stars
        } else if (iconInformation.contains("cloud")) {
            R.drawable.cloudy
        } else if (iconInformation.contains("snow")) {
            R.drawable.snow_branch
        } else if (iconInformation.contains("rain")) {
            R.drawable.rain_drops
        } else if (iconInformation.contains("fog")) {
            R.drawable.foggy
        } else {
            R.drawable.stars
        }
    }
}

