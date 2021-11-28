package com.example.lightappfragmentspagerlayout

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permissions {

fun getPermissions(activity_:Activity) {
    var REQUEST_CODE: Int = 0
    if (ContextCompat.checkSelfPermission(
            activity_ as MainActivity,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
        != PackageManager.PERMISSION_GRANTED
    ) {

        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity_ as MainActivity,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(
                activity_ as MainActivity,
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE
            )

            // REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    if (ContextCompat.checkSelfPermission(
            activity_ as MainActivity,
            android.Manifest.permission.RECORD_AUDIO
        )
        != PackageManager.PERMISSION_GRANTED
    ) {

        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity_ as MainActivity,
                android.Manifest.permission.RECORD_AUDIO
            )
        ) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(
                activity_ as MainActivity,
                arrayOf(android.Manifest.permission.RECORD_AUDIO),
                REQUEST_CODE
            )

            // REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }


    if (ContextCompat.checkSelfPermission(
            activity_ as MainActivity,
            android.Manifest.permission.MODIFY_AUDIO_SETTINGS
        )
        != PackageManager.PERMISSION_GRANTED
    ) {

        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                activity_ as MainActivity,
                android.Manifest.permission.MODIFY_AUDIO_SETTINGS
            )
        ) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(
                activity_ as MainActivity,
                arrayOf(android.Manifest.permission.MODIFY_AUDIO_SETTINGS),
                REQUEST_CODE
            )

            // REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }




    // Here, thisActivity is the current activity
    if (ContextCompat.checkSelfPermission( activity_ as MainActivity,
            android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {

        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity_ as MainActivity,
                android.Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity_ as MainActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE)

            // REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }



    if (ContextCompat.checkSelfPermission(activity_ as MainActivity,
            android.Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {

        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity_ as MainActivity,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity_ as MainActivity,
                arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION),
                REQUEST_CODE)

            // REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    if (ContextCompat.checkSelfPermission(activity_ as MainActivity,
            android.Manifest.permission.INTERNET)
        != PackageManager.PERMISSION_GRANTED) {

        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity_ as MainActivity,
                android.Manifest.permission.INTERNET)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity_ as MainActivity,
                arrayOf(android.Manifest.permission.INTERNET),
                REQUEST_CODE)

            // REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }


    if (ContextCompat.checkSelfPermission(activity_ as MainActivity,
            android.Manifest.permission.ACCESS_WIFI_STATE)
        != PackageManager.PERMISSION_GRANTED) {

        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity_ as MainActivity,
                android.Manifest.permission.ACCESS_WIFI_STATE)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity_ as MainActivity,
                arrayOf(android.Manifest.permission.ACCESS_WIFI_STATE),
                REQUEST_CODE)

            // REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    if (ContextCompat.checkSelfPermission(activity_ as MainActivity,
            android.Manifest.permission.CHANGE_WIFI_STATE)
        != PackageManager.PERMISSION_GRANTED) {

        // Permission is not granted
        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity_ as MainActivity,
                android.Manifest.permission.CHANGE_WIFI_STATE)) {
            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
        } else {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(activity_ as MainActivity,
                arrayOf(android.Manifest.permission.CHANGE_WIFI_STATE),
                REQUEST_CODE)

            // REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }


    
    
}
}