package com.example.lightappfragmentspagerlayout

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class scanwifi (var activity_: Activity){
    lateinit var wifiManager: WifiManager
    lateinit var mContext:Context
    lateinit var resultList : ArrayList<ScanResult>
    var scandone:Boolean=false
    lateinit var list: ArrayList<String>


    init {

        var REQUEST_CODE: Int =0
        mContext = activity_!!.applicationContext
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(mContext,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale( activity_ as MainActivity,
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



        if (ContextCompat.checkSelfPermission(mContext,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(mContext as MainActivity,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(mContext as MainActivity,
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
            if (ActivityCompat.shouldShowRequestPermissionRationale(mContext as MainActivity,
                            android.Manifest.permission.INTERNET)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(mContext as MainActivity,
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
            if (ActivityCompat.shouldShowRequestPermissionRationale(mContext as MainActivity,
                            android.Manifest.permission.ACCESS_WIFI_STATE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(mContext as MainActivity,
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
            if (ActivityCompat.shouldShowRequestPermissionRationale(mContext as MainActivity,
                            android.Manifest.permission.CHANGE_WIFI_STATE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(mContext as MainActivity,
                        arrayOf(android.Manifest.permission.CHANGE_WIFI_STATE),
                        REQUEST_CODE)

                // REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        this.wifiManager = mContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }


    val broadcastReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onReceive(contxt: Context?, intent: Intent?) {
            resultList = wifiManager.scanResults as ArrayList<ScanResult>
            //Toast.makeText(this, "onReceive Called", Toast.LENGTH_SHORT).show()
            stopScanning()

        }
    }

    fun startScanning() {
        scandone=false
        wifiManager.startScan()
        mContext.registerReceiver(broadcastReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
    }


    fun stopScanning() {
        mContext.unregisterReceiver(broadcastReceiver)
        /*  val axisList = ArrayList<Axis>()*/
        var ll: String

        ll=""
        list = ArrayList<String>()

        for (result in resultList) {
            ll= result.SSID.toString()
            if (("MobileD_" in ll)) {
                //+ " " + result.BSSID.toString() + " " + result.level.toString()
                list.add(ll) //result.BSSID + " " + result.level)
            }
        }

    scandone=true
    }

}