package com.example.cameraxapp

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.cameraxapp.databinding.ActivityEmailPopUpBinding
import com.example.cameraxapp.databinding.ActivityImageFilterBinding
import java.io.File

class EmailPopUpActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityEmailPopUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityEmailPopUpBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val sharedPreference =  getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val userEmailId= sharedPreference.getString("userEmailId","defaultName")
        viewBinding.emailId.text = userEmailId.toString()
        viewBinding.cameraButton.setOnClickListener{
            deleteInternalStorageDirectoryy()
            val intent = Intent(this@EmailPopUpActivity,MainActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onBackPressed() {
        // Start MainActivity
        deleteInternalStorageDirectoryy()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        // Optional: if you want to finish the current activity
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        deleteInternalStorageDirectoryy()

    }

    fun deleteInternalStorageDirectoryy() {
        if (ContextCompat.checkSelfPermission(
                this@EmailPopUpActivity,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED
        ) {
            val input_pathh = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString() + "/CameraX-Image/"
            )


            val input_path = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString() + "/CameraX-Image-Input/"
            )
            val output_pathh = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString() + "/CameraX-Image-Output/"
            )
            if (input_path.exists()) {
                input_path.deleteRecursively()
            }
            if (input_pathh.exists()) {
                input_pathh.deleteRecursively()
            }
            if (output_pathh.exists()) {
                output_pathh.deleteRecursively()
            }
        } else {
            requestRuntimePermissionn()
        }
    }


    private fun requestRuntimePermissionn(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this@EmailPopUpActivity,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this@EmailPopUpActivity,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                14
            )
            return false
        }
        return true
    }

}