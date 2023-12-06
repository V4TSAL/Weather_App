package com.example.weather_app.weather

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
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
import com.example.weather_app.database.WeatherDao
import com.example.weather_app.database.WeatherTable
import com.example.weather_app.fonts.Fonts
import com.example.weather_app.navgraph.Screen

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ChooseLocation(
    navController: NavController,
    viewModel: WeatherViewModel,
    sharedPreferences: SharedPreferences,
    weatherDao: WeatherDao
) {
    var location by remember {
        mutableStateOf("")
    }
    var loader by remember {
        mutableStateOf(false)
    }
    val scaffoldState = rememberScaffoldState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()
    androidx.compose.material.Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    androidx.compose.material.Text(
                        "The Weather App",
                        fontFamily = Fonts.boldFontFamily,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )

                },
                backgroundColor = Color.Black
            )
        },
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .paint(
                    contentScale = ContentScale.Crop,
                    painter = rememberAsyncImagePainter(
                        ImageRequest
                            .Builder(context)
                            .data(data = R.drawable.rain_fall)
                            .apply(block = {
                                size(Size.ORIGINAL)
                            })
                            .build(), imageLoader = imageLoader
                    ),
                    colorFilter = ColorFilter.colorMatrix(
                        ColorMatrix().apply { setToScale(0.5f, 0.5f, 0.5f, 1f) })
                )

        ) {
            if (loader) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Spacer(modifier = Modifier.height(25.dp))
                Text(
                    "Enter the name of a place",
                    fontSize = 25.sp,
                    fontFamily = Fonts.boldFontFamily,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(150.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color.White,
                            unfocusedBorderColor = Color.White,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.Transparent),
                        value = location,
                        onValueChange = {
                            location = it
                        },
                        label = { Text("Enter your location", color = Color.White) },
                        textStyle = TextStyle(
                            color = Color.White, fontFamily = Fonts.boldFontFamily,
                        ),
                    )
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        onClick = {
                            loader = true
                            CurrentLocation.currentLocation = location
                            keyboardController?.hide()
                            viewModel.getWeatherFromApi {
                                if (it) {
                                    var allLocations = sharedPreferences.getStringSet(
                                        "ALL_LOCATIONS",
                                        mutableSetOf<String>()
                                    )
                                    allLocations!!.add(location.lowercase())
                                    sharedPreferences.edit().apply {
                                        putString("CURRENT_LOCATION", location)
                                        putStringSet("ALL_LOCATIONS", allLocations)
                                        weatherDao.insertWeather(WeatherTable(location=location,weather= viewModel.liveApiData.value!![0]))
                                    }.apply()
                                    navController.popBackStack()
                                    navController.navigate(Screen.WeatherApp.route)
                                } else {
                                    loader = false
                                    scaffoldState.snackbarHostState.showSnackbar("Could not find the location")
                                }
                            }
                        }) {
                        Text(text = "Submit", fontFamily = Fonts.boldFontFamily)
                    }
                }


            }
        }


    }

}