package com.example.try5

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScreenlockActivity : AppCompatActivity() {

    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var componentName: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.screenlock)

        // Initialize DevicePolicyManager
        devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        componentName = ComponentName(this, MyAdminReceiver::class.java)

        // Initialize Views
        val pinInput = findViewById<EditText>(R.id.pinInput)
        val confirmPinInput = findViewById<EditText>(R.id.confirmPinInput)
        val setPinButton = findViewById<Button>(R.id.setPinButton)
        val lockScreenButton = findViewById<Button>(R.id.lockScreenButton)

        // Set PIN Button
        setPinButton.setOnClickListener {
            val pin = pinInput.text.toString()
            val confirmPin = confirmPinInput.text.toString()

            if (pin.isNotEmpty() && pin == confirmPin) {
                if (devicePolicyManager.isAdminActive(componentName)) {
                    // Set the PIN
                    val success = devicePolicyManager.resetPassword(pin, DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY)
                    if (success) {
                        Toast.makeText(this, "PIN set successfully!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Failed to set PIN.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Admin rights not granted. Please activate admin first.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "PINs do not match. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }

        // Lock Screen Button
        lockScreenButton.setOnClickListener {
            if (devicePolicyManager.isAdminActive(componentName)) {
                // Lock the screen
                devicePolicyManager.lockNow()
                Toast.makeText(this, "Screen locked!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Admin rights not granted. Please activate admin first.", Toast.LENGTH_SHORT).show()
            }
        }

        // Adjust padding for insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun requestAdminRights() {
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Enable admin rights to set a PIN and lock the screen.")
        startActivityForResult(intent, REQUEST_CODE_ENABLE_ADMIN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ENABLE_ADMIN) {
            if (devicePolicyManager.isAdminActive(componentName)) {
                Toast.makeText(this, "Admin rights granted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Admin rights not granted. Cannot set PIN or lock screen.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_ENABLE_ADMIN = 1001
    }
}