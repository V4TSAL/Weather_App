package com.example.weather_app.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.weather_app.R
import com.example.weather_app.weather.CurrentLocationWeather

class RunningServices : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            Actions.STOP.toString() ->stopSelf()
            Actions.START.toString() -> start()
        }

        return super.onStartCommand(intent, flags, startId)
    }
    private fun start(){
        val notification = NotificationCompat.Builder(this,"running_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setContentTitle("${CurrentLocationWeather.currentTemperature.doubleValue} ${CurrentLocationWeather.currentWeather.value}")
            .setContentText(CurrentLocationWeather.currentLocation.value)
            .setAutoCancel(true)
            .build()
        startForeground(1,notification)
    }
    enum class Actions{
        START,STOP
    }
}