package com.example.weather_app.weather

import android.content.Context
import android.content.Intent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.actionStartActivity
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.weather_app.MainActivity

class WeatherWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Box(
                contentAlignment = Alignment.Center, modifier = GlanceModifier
                    .background(Color.White.copy(alpha = 0.15f))
                    .fillMaxSize()
                    .padding(8.dp)
                    .clickable(onClick = actionStartActivity<MainActivity>())
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "${CurrentLocationWeather.currentTemperature.doubleValue} ° C",
                        style = TextStyle(
                            color = ColorProvider(color = Color.White),
                            fontSize = 26.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = CurrentLocationWeather.currentWeather.value,
                        style = TextStyle(
                            color = ColorProvider(color = Color.White),
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                    Text(
                        text = CurrentLocationWeather.currentLocation.value,
                        style = TextStyle(
                            color = ColorProvider(color = Color.White),
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
        }
    }
}