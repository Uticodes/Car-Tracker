package com.example.vehicletracker.utils

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.net.ConnectivityManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        //initPrefLib()
        createNotificationChannel()

    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Car Tracker Drivers Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.shouldVibrate()
            channel.description = "Notify users on important updates with vibration and sound"

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)

        }
    }

//    private fun initPrefLib() {
//        Builder()
//            .setContext(this)
//            .setMode(ContextWrapper.MODE_PRIVATE)
//            .setPrefsName(packageName)
//            .setUseDefaultSharedPreference(true)
//            .build()
//    }

    companion object {
        val NOTIFICATION_CHANNEL_ID = "com.example.vehicletracker_NOTIFICATION_CHANNEL_ID"
        //var appUser: User? = null

        fun checkNetwork(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}
