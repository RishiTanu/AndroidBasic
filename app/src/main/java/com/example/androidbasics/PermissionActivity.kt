package com.example.androidbasics

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class PermissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)


    }

    private fun hasWriteExternalStoragePermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED


    private fun hasLocationForegroundPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun hasLocationBackgroundPermission() =
        ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


    private fun requestPermission() {
        val permissionRequest = mutableListOf<String>()
        if (!hasLocationBackgroundPermission()) {
            permissionRequest.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
        if (!hasLocationForegroundPermission()) {
            permissionRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
        if (!hasWriteExternalStoragePermission()) {
            permissionRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if(permissionRequest.isNotEmpty()){
            ActivityCompat.requestPermissions(this,permissionRequest.toTypedArray(),0)
        }
    }



    //explicit intent receiving.... override fun onRequestPermissionsResult(
    //        requestCode: Int,
    //        permissions: Array<out String>,
    //        grantResults: IntArray
    //    ) {
    //        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    //        if(requestCode==0  && grantResults.isNotEmpty()){
    //           for(i in grantResults.indices){
    //               if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
    //                   Log.d("TAG", "onRequestPermissionsResult: ${permissions[i]} granded")
    //               }
    //           }
    //        }
    //    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 0){

        }
    }
}