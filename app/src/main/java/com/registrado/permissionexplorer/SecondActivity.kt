package com.registrado.permissionexplorer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.registrado.permissionexplorer.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    private val takePicture = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Add content here for processing the captured photo.
            showToast("Picture taken successfully")
        } else {
            showToast("Picture wasn't taken")
        }
    }

    private companion object {
        const val CAMERA_PERMISSION_REQUEST_CODE = 100
        const val LOCATION_PERMISSION_REQUEST_CODE = 101
        const val MICROPHONE_PERMISSION_REQUEST_CODE = 104
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bttnCamera.setOnClickListener {
            camera()
        }

        binding.bttnLocation.setOnClickListener {
            location()
        }

        binding.bttnMicrophone.setOnClickListener{
            microphone()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun camera() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
            )
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            openCamera()
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            try {
                takePicture.launch(takePictureIntent)
            } catch (e: Exception) {
                showToast("Error: ${e.message}")
                finish()
            }
        }
    }

    private fun location() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            openLocation()
        }
    }

    private fun openLocation() {
        // Permission is already granted
        // You can perform your location-related task here.
        showToast("Location permission already granted")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            CAMERA_PERMISSION_REQUEST_CODE -> handleCameraPermissionResult(grantResults)
            LOCATION_PERMISSION_REQUEST_CODE -> handleLocationPermissionResult(grantResults)
            MICROPHONE_PERMISSION_REQUEST_CODE -> handleMicrophonePermissionResult(grantResults)
        }
    }

    private fun handleCameraPermissionResult(grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            showToast("Camera permission denied")
        }
    }

    private fun handleLocationPermissionResult(grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openLocation()
        } else {
            showToast("Location permission denied")
        }
    }

    private fun microphone() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                MICROPHONE_PERMISSION_REQUEST_CODE
            )
        } else {
            openMicrophone()
        }
    }

    private fun openMicrophone() {
        // Permission is already granted
        // You can perform your microphone-related task here.
        showToast("Microphone permission already granted")
    }

    private fun handleMicrophonePermissionResult(grantResults: IntArray) {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openMicrophone()
        } else {
            showToast("Microphone permission denied")
        }
    }
}
