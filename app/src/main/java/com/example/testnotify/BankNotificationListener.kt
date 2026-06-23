package com.example.testnotify

import android.Manifest
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.speech.tts.TextToSpeech
import java.util.Locale


class BankNotificationListener : NotificationListenerService() {

    private val gmailPackage = "com.google.android.gm"
    private lateinit var tts: TextToSpeech
    private var ttsReady = false

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onNotificationPosted(sbn: StatusBarNotification) {

// Ignoruj powiadomienia własnej aplikacji
        if (sbn.packageName == applicationContext.packageName) {
            return
        }

        // Reaguj tylko na Gmail
        if (sbn.packageName == gmailPackage) {

            val message = "Hej, Gmail właśnie wpłynął"
            speak(message)
            val notification = NotificationCompat.Builder(this, "testnotify_channel")
                .setSmallIcon(android.R.drawable.ic_dialog_email)
                .setContentTitle("📧 Gmail")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()


            NotificationManagerCompat.from(this).notify(
                (System.currentTimeMillis() % 100000).toInt(),
                notification
            )


        }
    }

    override fun onCreate() {
        super.onCreate()

        tts = TextToSpeech(this) { status ->
            if (status == TextToSpeech.SUCCESS) {

                val result = tts.setLanguage(Locale("pl", "PL"))

                ttsReady = result != TextToSpeech.LANG_MISSING_DATA &&
                        result != TextToSpeech.LANG_NOT_SUPPORTED
            }
        }
    }

    private fun speak(text: String) {

        if (!ttsReady) return

        tts.speak(
            text,
            TextToSpeech.QUEUE_FLUSH,
            null,
            "testnotify_tts"
        )
    }
    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }
}




