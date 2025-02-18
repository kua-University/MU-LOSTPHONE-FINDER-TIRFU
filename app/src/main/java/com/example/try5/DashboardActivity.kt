package com.example.try5

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.try5.R

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainmenu)

        // Find buttons
        val alarm = findViewById<Button>(R.id.btnAlarmSound)
        val report = findViewById<Button>(R.id.btnReportPolice)
        val btnTrackPhone = findViewById<Button>(R.id.btnTrackPhone)
        val btnLockPhone = findViewById<Button>(R.id.btnLockPhone)
        val btnEraseData = findViewById<Button>(R.id.btnEraseData)
        val btnSettings = findViewById<Button>(R.id.btnSettings)
        val btnSeePicture = findViewById<Button>(R.id.btnSeePicture)

        // Set click listeners for each button
        alarm.setOnClickListener {
            makeAlarmSound()
        }

        report.setOnClickListener {
            sendLostMessageReport()
        }

        btnTrackPhone.setOnClickListener {
            startActivity(Intent(this, MapViewActivity::class.java))
        }

        btnLockPhone.setOnClickListener {
            startActivity(Intent(this, ScreenlockActivity::class.java))
        }

        btnEraseData.setOnClickListener {
            eraseData()
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        btnSeePicture.setOnClickListener {
            startActivity(Intent(this, SuspectActivity::class.java))
        }
    }

    private fun makeAlarmSound() {
        // Play alarm sound logic
        val mediaPlayer = MediaPlayer.create(this, R.raw.alarm) // Ensure the alarm sound file exists in res/raw
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            Toast.makeText(this, "Alarm sound playing...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendLostMessageReport() {
        val phoneNumber = "0970805789" // Replace with actual phone number
        val message = "I lost my phone. Please help!"

        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this, "Lost message sent", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send message: ${e.message}", Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }


    private fun eraseData() {
        // Placeholder for erase data logic
        Toast.makeText(this, "Erase data functionality triggered.", Toast.LENGTH_SHORT).show()
    }
}
