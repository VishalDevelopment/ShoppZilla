package com.example.shoppingapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import org.chromium.base.compat.ApiHelperForM.getSystemService


@HiltAndroidApp
class App() : Application() {
    companion object{

    val channelId = "Order Placed"
    lateinit var notificationManager: NotificationManager
    }

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                "Order Completion",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}