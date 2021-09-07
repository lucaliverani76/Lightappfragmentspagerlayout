package com.example.lightappfragmentspagerlayout

import android.app.Activity
import android.content.Context
import android.net.DhcpInfo
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.TextView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.InvocationTargetException
import java.net.*
import java.util.*
import java.util.regex.Pattern
import kotlin.Boolean.*


class UDPBroadcaster(var mContext: Context) {
    private val TAG:String = UDPBroadcaster::class.java.simpleName
    private var mDestPort = 0
    private var mSocket: DatagramSocket? = null

    public var listofdevices: MutableList<MutableList<String>>? = null

    val SEND_PORT: Int = 8232
    val DEST_PORT: Int = 8232
    val LOCAL_PORT:Int = 8232 /* must be equal to LOCAL_PORT */
    var IP:String="0.0.0.0"

    var isClosed: Boolean = false
    var sendBuffer: String = "{\"red\":23, \"green\":23 , \"blue\":100, , \"alpha\":76}"

    var LIGHT:lightinformation?=null

    //private val ROOT_PATH:String = Environment.getExternalStorageDirectory().path
    /**
     * turn on
     */
    fun open(localPort: Int = LOCAL_PORT, destPort: Int,  _IP:String="0.0.0.0",
             lIGHT:lightinformation?=null): Boolean {
        isClosed = false
        mDestPort = destPort
        IP=_IP
        if (lIGHT!=null)
            {LIGHT=lIGHT}
        try {
            mSocket = DatagramSocket(localPort)
            mSocket?.broadcast = true
            mSocket?.reuseAddress = true
            return true
        } catch (e: SocketException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * shut down
     */
    fun close(activity: Activity): Boolean {
        if (mSocket != null && mSocket?.isClosed?.not() as Boolean) {
            mSocket?.close()
        }

        val sharedPreference =  activity.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putString("username","Anupam")
        editor.putLong("l",100L)
        editor.commit()
        return true
    }


    fun close(): MutableList<MutableList<String>>? {
        if (mSocket != null && mSocket?.isClosed?.not() as Boolean) {
            mSocket?.close()
            closeUDPBroadcast()
        }

        return listofdevices
    }

    /**
     * Send broadcast package
     */
    fun sendPacket(buffer: ByteArray): Boolean {
        try {
            val addr = getBroadcastAddress(mContext)
            Log.d("$TAG addr",addr.toString())
            val packet = DatagramPacket(buffer, buffer.size)
            packet.address = addr
            packet.port = mDestPort
            mSocket?.send(packet)
            return true
        } catch (e1: UnknownHostException) {
            e1.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }


    fun sendPacketx(buffer: ByteArray): Boolean {
        try {
            val addr = InetAddress.getByName(IP)
            Log.d("$TAG addr",addr.toString())
            val packet = DatagramPacket(buffer, buffer.size)
            packet.address = addr
            packet.port = mDestPort
            mSocket?.send(packet)
            return true
        } catch (e1: UnknownHostException) {
            e1.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    /**
     * Receive broadcast
     */
    fun recvPacket(buffer: ByteArray): Boolean {
        val packet = DatagramPacket(buffer, buffer.size)
        try {
            mSocket?.receive(packet)
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return false
    }

    companion object {
        /**
         * Get broadcast address
         */
        fun getBroadcastAddress(context: Context): InetAddress {
            if (isWifiApEnabled(context)) { //Determine whether the wifi hotspot is turned on
                return InetAddress.getByName("192.168.43.255")  //Directly return
            }
            val wifi: WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val dhcp: DhcpInfo = wifi.dhcpInfo ?: return InetAddress.getByName("255.255.255.255")
            val broadcast = (dhcp.ipAddress and dhcp.netmask) or dhcp.netmask.inv()
            val quads = ByteArray(4)
            for (k in 0..3) {
                quads[k] = ((broadcast shr k * 8) and 0xFF).toByte()
            }
            return InetAddress.getByAddress(quads)
        }

        /**
         * check whether the wifiAp is Enable
         */
        private fun isWifiApEnabled(context: Context): Boolean {
            try {
                val manager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
                val method = manager.javaClass.getMethod("isWifiApEnabled")
                return method.invoke(manager) as Boolean
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }
            return false
        }
    }



    public fun closeUDPBroadcast() {
        isClosed = true
    }


    public fun sendUDPBroadcast() {

        this.open(SEND_PORT, DEST_PORT) //Open the broadcast
        val buffer: ByteArray = sendBuffer.toByteArray()
        Thread(Runnable {
            while (!isClosed) {
                try {
                    Thread.sleep(50) //500ms delay
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                this.sendPacket(buffer) //Send broadcast packet
                Log.v("TAG","$TAG data: ${String(buffer)}")
            }
            this.close() //Close the broadcast
        }).start()
    }


    public fun sendUDP() {
        var cc:Int=0
        var offn:Int=0
        //this.open(SEND_PORT, DEST_PORT) //Open the broadcast
        var buffer: ByteArray = LIGHT?.stringtosend!!.toByteArray()
        var stringold=LIGHT?.stringtosend
        Thread(Runnable {
            while (!isClosed) {
                try {
                    Thread.sleep(20) //500ms delay
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                buffer = LIGHT?.stringtosend!!.toByteArray()
                if (LIGHT?.stringtosend==stringold){cc=kotlin.math.min(cc+1,3)}
                else {cc=0}
                if (LIGHT?.stateonoff==false){offn=kotlin.math.min(offn+1,3)}
                else {offn=0}

                if (cc<3 && offn<3)
                {this.sendPacketx(buffer)} //Send broadcast packet
                //Log.v("TAG","$TAG data: ${String(buffer)}")
                stringold=LIGHT?.stringtosend
            }
            this.close() //Close the broadcast
        }).start()
    }


    public fun recvUDPBroadcast() {

        this.open(LOCAL_PORT,DEST_PORT)
        var buffer:ByteArray = kotlin.ByteArray(1024)
        val packet = DatagramPacket(buffer, buffer.size)
        Thread(Runnable {
            while (!isClosed){
                try{
                    Thread.sleep(50) //500ms delay
                }catch (e:Exception){e.printStackTrace()}
                this.recvPacket(packet.data) //Receive broadcast
                val data:String = String(packet.data)
                var devicecharacteristics: List<String> = data.split("**")
                if ((devicecharacteristics.size>=1) &&(devicecharacteristics[0]=="$%LL%$")) {

                    devicecharacteristics=devicecharacteristics.drop(1)

                    var templist=devicecharacteristics.toMutableList()

                    if (listofdevices == null) {
                        listofdevices = mutableListOf<MutableList<String>>()
                        listofdevices?.add(templist)

                    } else {
                        var trovato: Boolean = false
                        for (k in listofdevices!!) {
                            if (devicecharacteristics[0].equals(k[0]))
                                trovato = true
                        }
                        if (trovato == false)
                            listofdevices?.add(templist)

                    }
                    Log.v("TAG", "$TAG data: $data")
                    Log.v("TAG", "$TAG addr: ${packet.address}")
                    Log.v("TAG", "$TAG port: ${packet.port}")
                }
                
            }

            this.close() //Quit receiving broadcast
        }).start()
    }
}