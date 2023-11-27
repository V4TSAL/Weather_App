package com.example.weather_app.weather

import android.inputmethodservice.Keyboard.Row
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.example.composepractise.weather.WeatherViewModel
import com.example.weather_app.R
import com.example.weather_app.fonts.Fonts
import com.example.weather_app.models.WeatherData
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeatherUi(viewModel: WeatherViewModel) {
    val weatherData = viewModel.liveApiData.observeAsState()
    val iconInformation = weatherData.value!![0].currentConditions.icon
    val keyboardController = LocalSoftwareKeyboardController.current
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

    var currentImage =
        if (iconInformation.contains("day")) {
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
    if (weatherData.value.isNullOrEmpty()) {
        Box(
            modifier = Modifier
                .background(Color(0xFF141414))
                .fillMaxSize()
        ) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    contentScale = ContentScale.Crop,
                    painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(context)
                            .data(data = currentImage)
                            .apply(block = {
                                size(Size.ORIGINAL)
                            })
                            .build(), imageLoader = imageLoader
                    ),
                    colorFilter = ColorFilter.colorMatrix(
                        ColorMatrix().apply { setToScale(0.5f, 0.5f, 0.5f, 1f) })
                )
                .padding(12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CurrentWeather(weatherData = weatherData)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                weatherData.value!![0].days[0].description,
                fontSize = 20.sp,
                fontFamily = Fonts.boldFontFamily,
                color = Color.White,
                textAlign = TextAlign.Center
                )
            Spacer(modifier = Modifier.height(16.dp))
            AdditionalWeatherInformation(weatherData = weatherData)
        }
    }
}

@Composable
fun CurrentWeather(weatherData: State<ArrayList<WeatherData>?>) {
    val currentTemperatureCelsius =
        (((weatherData.value!![0].currentConditions.temp) - 32) * 5 / 9).toBigDecimal()
            .setScale(2, RoundingMode.UP).toDouble()
    val maxTemp = weatherData.value!![0].days[0].tempmax
    val minTemp = weatherData.value!![0].days[0].tempmin
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = Color.White.copy(alpha = 0.15f), shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = weatherData.value!![0].resolvedAddress,
                fontSize = 20.sp,
                fontFamily = Fonts.boldFontFamily,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = " $currentTemperatureCelsius ° C",
                fontSize = 36.sp,
                fontFamily = Fonts.boldFontFamily,
                color = Color.White
            )
            Text(
                text = weatherData.value!![0].currentConditions.conditions,
                fontSize = 20.sp,
                fontFamily = Fonts.boldFontFamily,
                color = Color.White
            )
            Row {
                Text(
                    text = "H ${convertToCelsius(maxTemp)} ° C ",
                    fontSize = 16.sp,
                    fontFamily = Fonts.normalFontFamily,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "L  ${convertToCelsius(minTemp)} ° C ",
                    fontSize = 16.sp,
                    fontFamily = Fonts.normalFontFamily,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun AdditionalWeatherInformation(weatherData: State<ArrayList<WeatherData>?>) {
    val maxTemp = weatherData.value!![0].days[0].tempmax
    val minTemp = weatherData.value!![0].days[0].tempmin
    val feelsLike = weatherData.value!![0].days[0].feelslike
    val cloudCover = weatherData.value!![0].days[0].cloudcover
    val humidity = weatherData.value!![0].days[0].humidity
    val solarEnergy = weatherData.value!![0].days[0].solarenergy
    val uvIndex = weatherData.value!![0].days[0].uvindex
    val visibility = weatherData.value!![0].days[0].visibility
    val precipitationProbability = weatherData.value!![0].days[0].precipprob
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(36.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                showAdditionalInformation(
                    heading = "Feels like: ",
                    description = "${convertToCelsius(feelsLike)} ° C "
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                showAdditionalInformation(
                    heading = "Cloud cover: ",
                    description = "$cloudCover %"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                showAdditionalInformation(
                    heading = "Humidity: ",
                    description = "${humidity} %"
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                showAdditionalInformation(
                    heading = "Solar energy: ",
                    description = "$solarEnergy"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                showAdditionalInformation(
                    heading = "UV index: ",
                    description = "$uvIndex"
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                showAdditionalInformation(
                    heading = "Visibility: ",
                    description = "$visibility Km"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            showAdditionalInformation(
                heading = "Precipitation probability: ",
                description = "$precipitationProbability %"
            )

        }
    }
}

fun convertToCelsius(temp: Double): Double {
    return ((temp - 32) * 5 / 9).toBigDecimal()
        .setScale(2, RoundingMode.UP).toDouble()
}

@Composable
fun showAdditionalInformation(heading: String, description: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = Color.White.copy(alpha = 0.15f), shape = RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        Column {
            Text(
                heading,
                fontSize = 20.sp,
                fontFamily = Fonts.boldFontFamily,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(description,
                fontSize = 16.sp,
                fontFamily = Fonts.normalFontFamily,
                color = Color.White,
                textAlign=TextAlign.Center
                )
        }
    }
}