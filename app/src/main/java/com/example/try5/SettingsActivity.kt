package com.example.try5

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        val btnEnableLocation = findViewById<Button>(R.id.btnEnableLocation)
        val btnSetLockScreenPassword = findViewById<Button>(R.id.btnSetLockScreenPassword)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnEnableLocation.setOnClickListener {
            Toast.makeText(this, "Location Enabled", Toast.LENGTH_SHORT).show()
        }

        btnSetLockScreenPassword.setOnClickListener {
            Toast.makeText(this, "Set Lock Screen Password", Toast.LENGTH_SHORT).show()
        }

        btnLogout.setOnClickListener {
            // Sign out from Firebase
            auth.signOut()

            // Redirect to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Close the current activity
        }
    }
}