package com.dignos.animal.activity5

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.dignos.animal.activity5.constants.Constants
import com.dignos.animal.activity5.databinding.ActivityPermissionBinding
import android.Manifest
import android.os.Build
import com.dignos.animal.activity5.constants.Constants.Companion.STORAGE_PERMISSION_CODE


class Permission : AppCompatActivity() {
    private lateinit var binding: ActivityPermissionBinding

    private val takePicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result -> if (result.resultCode == RESULT_OK){
            Toast.makeText(
                this,
                "Picture taken successfully",
                Toast.LENGTH_SHORT
            ).show()
        }else{
            Toast.makeText(
                this,
                "Picture wasn't taken",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPermissionBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.camera.setOnClickListener { camera() }
        binding.location.setOnClickListener { location() }
        binding.storage.setOnClickListener { requestStoragePermission() }

    }
    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                // Permission not granted, request it
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_CODE
                )
            } else {
                // Permission already granted
                Toast.makeText(
                    this,
                    "Storage permission already granted.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        } else {
            // For devices below Android M, no runtime permission is required
            Toast.makeText(
                this,
                "Storage permission is not required for this device.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.LOCATION_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openLocation()
            }else{
                Toast.makeText(
                    this,
                    "Location permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else if(requestCode == Constants.CAMERA_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera()
            }else{
                Toast.makeText(
                    this,
                    "Camera permission denied",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(
                    this,
                    "Storage permission granted. You can now access storage.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Permission denied
                Toast.makeText(
                    this,
                    "Storage permission denied. You cannot access storage.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun camera(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                Constants.CAMERA_PERMISSION_CODE
            )
        } else {
            openCamera()
        }
    }
    private fun openCamera(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            takePicture.launch(takePictureIntent)
        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            finish()
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
                Constants.LOCATION_PERMISSION_CODE
            )
        } else {
            openLocation()
        }
    }

    private fun openLocation() {
        Toast.makeText(
            this,
            "Location permission already granted",
            Toast.LENGTH_SHORT
        ).show()
    }
}