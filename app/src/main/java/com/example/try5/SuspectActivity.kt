package com.example.try5

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import com.bumptech.glide.Glide

class SuspectActivity : AppCompatActivity() {

    private lateinit var cameraHelper: CameraHelper
    private lateinit var previewView: PreviewView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suspect)

        previewView = findViewById(R.id.previewView)
        cameraHelper = CameraHelper(this, previewView)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val btnCapturePhoto = findViewById<Button>(R.id.btnCapturePhoto)

        // Retrieve the image URI from the intent (if any)
        val imageUri = intent.getStringExtra("image_path")
        if (imageUri != null) {
            Glide.with(this).load(Uri.parse(imageUri)).into(imageView)
        }

        // Start camera
        cameraHelper.startCamera()

        // Capture photo and save it
        btnCapturePhoto.setOnClickListener {
            cameraHelper.takePictureAndSave { savedUri ->
                Toast.makeText(this, "Photo saved: $savedUri", Toast.LENGTH_SHORT).show()
                // Load saved photo into ImageView
                Glide.with(this).load(savedUri).into(imageView)
            }
        }
    }
}
