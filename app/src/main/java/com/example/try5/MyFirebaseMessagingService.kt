package com.example.try5

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Check if the message contains a data payload
        remoteMessage.data.isNotEmpty().let {
            val command = remoteMessage.data["command"]
            Log.d("FCM", "Received command: $command")

            when (command) {
                "TAKE_PICTURE" -> {
                    // Trigger the SuspectActivity to take a picture
                    triggerTakePicture(applicationContext)
                }
                "SOUND_ALARM" -> {
                    // Trigger alarm sound
                    playAlarmSound(applicationContext)
                }
                else -> {
                    Log.d("FCM", "Unknown command received: $command")
                }
            }
        }
    }

    override fun onNewToken(token: String) {
        // Log or send the token to your backend if needed
        Log.d("FCM", "New token: $token")
    }

    private fun triggerTakePicture(context: Context) {
        // Launch SuspectActivity to handle the "take picture" action
        val intent = Intent(context, SuspectActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) // Required since we're starting an activity from a non-UI context
            putExtra("trigger_picture", true)
        }
        context.startActivity(intent)
    }

    private fun playAlarmSound(context: Context) {
        // Play the alarm sound
        val mediaPlayer = MediaPlayer.create(context, R.raw.alarm)
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            Log.d("FCM", "Alarm sound is playing.")
        }
    }
}
