package com.example.testnotify

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createChannel()

        // 👉 od razu prośba o dostęp do powiadomień

        //TODO()://startActivity("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")

        startActivity(Intent(this, Home::class.java))
        finish()

    }

    private fun createChannel() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            val channel = android.app.NotificationChannel(
                "testnotify_channel",
                "TestNotify Alerts",
                android.app.NotificationManager.IMPORTANCE_HIGH
            )

            val manager = getSystemService(android.app.NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}