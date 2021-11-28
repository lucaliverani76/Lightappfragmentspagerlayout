package com.example.lightappfragmentspagerlayout

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.ScanResult
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class scanwifi(var activity_: Activity){
    lateinit var wifiManager: WifiManager
    lateinit var mContext:Context
    lateinit var resultList : ArrayList<ScanResult>
    var scandone:Boolean=false
    lateinit var list: ArrayList<String>

    var wifiSSID:String=""
    var  wifiPassword:String=""


    init {
        mContext = activity_!!.applicationContext
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
        wifiManager.getConnectionInfo()
        var ll: String

        ll=""
        list = ArrayList<String>()

        for (result in resultList) {
            ll= result.SSID.toString()
            if (("MobileD_" in ll)  && !(ll in wifiManager.connectionInfo.ssid)) {
                //+ " " + result.BSSID.toString() + " " + result.level.toString()
                list.add(ll) //result.BSSID + " " + result.level)
            }
        }

    scandone=true
    }

    fun tryconnection(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

            WiFiConnectUseCase(mContext).invoke()

        } else
        {
            WiFiConnectLegacyUseCase(mContext).invoke()
        }


    }


    /* FROM HERE I PUT THE TRY CONNECTION  */

    @Suppress("DEPRECATION")
    inner  class WiFiConnectLegacyUseCase(private val context: Context) {

        private var wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        private val connectivityManager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager



        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        operator fun invoke() {
            connect()
            initNetwork()
        }

        private fun connect(){
            val intentFilter = IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION)
            val receiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    if (isConnectedToCorrectSSID()) {
                        Log.v("TAG", "Successfully connected to the device.")
                        //emitter.onComplete()
                    } else {
                        Log.i("TAG", "Still not connected to ${wifiSSID}. Waiting a little bit more...")
                    }
                }
            }
            Log.i("TAG", "Registering connection receiver...")
            context.registerReceiver(receiver, intentFilter)
            // emitter.setCancellable {
            // Log.i("TAG","Unregistering connection receiver...")
            //  context.unregisterReceiver(receiver)
            //}
            addNetwork()
        }


        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        private fun initNetwork(): Network? {
            Log.i("TAG", "Initializing network...")
            var network: Network? =null
            var networks  = connectivityManager.allNetworks
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                for (it_: Network in networks)
                {
                    var cmc = connectivityManager.getNetworkCapabilities(it_)
                    if (cmc != null) {
                        if (cmc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            network=it_
                            break
                        }
                    }
                }
            }
            else
            {
                for (it_: Network in networks)
                {
                    if (connectivityManager.getNetworkInfo(it_)!!.extraInfo == wifiSSID)
                    {
                        network = it_
                        break
                    }
                }
            }

            return network
        }

        private fun addNetwork() {
            disconnectCurrentNetwork()
            Log.i("TAG", "Connecting to ${wifiSSID}...")
            val wc = WifiConfiguration()
            wc.SSID = "\"" + wifiSSID + "\""
            wc.preSharedKey = "\"" + wifiPassword + "\""
            wc.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK)
            val netId = wifiManager.addNetwork(wc)
            if (netId != -1) {
                if (!wifiManager.enableNetwork(netId, true)) {
                    Log.i("TAG", "Failed to connect to the device.")
                    //emitter.tryOnError(IllegalArgumentException("Failed to connect to the device"))
                }
            } else {
                Log.i("TAG", "Failed to connect to the device. addNetwork() returned -1")
                //emitter.tryOnError(IllegalArgumentException("Failed to connect to the device. addNetwork() returned -1"))

            }
        }

        private fun isConnectedToCorrectSSID(): Boolean {
            val currentSSID = wifiManager.connectionInfo.ssid ?: return false
            Log.i("TAG", "Connected to $currentSSID")

            var res=(currentSSID == "\"${wifiSSID}\"")
            return res
        }


        fun disconnectCurrentNetwork(): Boolean {
            if (wifiManager != null && wifiManager.isWifiEnabled()) {
                val netId: Int = wifiManager.getConnectionInfo().getNetworkId()
                wifiManager.disableNetwork(netId)
                return wifiManager.disconnect()
            }
            return false
        }
    }



    inner class WiFiConnectUseCase(context: Context) {

        private var wifiManager =
            context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        private val connectivityManager =
            context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        @RequiresApi(api = Build.VERSION_CODES.Q)
        operator fun invoke(): Pair<Network?, ConnectivityManager.NetworkCallback> { //return the NetworkCallback in order to disconnect properly from the device
            return connect()
            //delay(3, TimeUnit.SECONDS) // wait for 3 sec, just to make sure everything is configured on the device
        }

        @RequiresApi(Build.VERSION_CODES.Q)
        private fun connect(): Pair<Network?, ConnectivityManager.NetworkCallback> {
            disconnectCurrentNetwork()
            var networkx: Network?=null

            val specifier = WifiNetworkSpecifier.Builder()
                .setSsid(wifiSSID)
                .setWpa2Passphrase(wifiPassword)
                .build()

            val networkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .removeCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) // as not internet connection is required for this device
                .setNetworkSpecifier(specifier)
                .build()

            val networkCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Log.i("TAG", "connect to WiFi success. Network is available.")
                    //emitter.onSuccess(Pair(network, this))
                    networkx=network
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    Log.i("TAG", "connect to WiFi failed. Network is unavailable")
                    //emitter.tryOnError(IllegalArgumentException("connect to WiFi failed. Network is unavailable"))
                }
            }

            connectivityManager.requestNetwork(networkRequest, networkCallback)


            return Pair(networkx, networkCallback)
        }


        fun disconnectCurrentNetwork(): Boolean {
            if (wifiManager != null && wifiManager.isWifiEnabled()) {
                val netId: Int = wifiManager.getConnectionInfo().getNetworkId()
                wifiManager.disableNetwork(netId)
                return wifiManager.disconnect()
            }
            return false
        }
    }

}