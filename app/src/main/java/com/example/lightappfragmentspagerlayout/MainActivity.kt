package com.example.lightappfragmentspagerlayout

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.IBinder
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import android.provider.Settings;



class EventViewModel : ViewModel() {
    //...
    var insertionId = MutableLiveData<Float>()

    fun insert(num:Float) {
            insertionId.value=num
    }
}

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> FirstFragment.newInstance("0", "Page # 1")
            1 -> FirstFragment.newInstance("1", "Page # 2")
            2 -> SecondFragment()
            else -> SecondFragment()
        }
    }

    override fun getItemCount(): Int {
        return CARD_ITEM_SIZE
    }

    companion object {
        private const val CARD_ITEM_SIZE = 3
    }
}


class MainActivity : AppCompatActivity() {

    lateinit var  vpPager: ViewPager2
    lateinit var text_: TextView
    lateinit var but: Button
    lateinit var Aview:AndroidViewModel
    var flipflop:Boolean = true
    var counter:Int=0

    lateinit var intentx:Intent



    lateinit var viewModel: EventViewModel

    public fun setdata(r:Int){counter=r}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.lightappfragmentspagerlayout.R.layout.activity_main)

        vpPager = findViewById<View>(com.example.lightappfragmentspagerlayout.R.id.vpPager) as ViewPager2
        vpPager.setAdapter(createAdapter())

        text_ = findViewById<View>(R.id.textView2) as TextView
        but = findViewById<View>(R.id.button) as Button
        viewModel= EventViewModel()
        Aview= AndroidViewModel()


        but.setOnClickListener() {


            if (flipflop) {
                val context: Context = it.getContext()
                text_.setText(counter.toString())
                Aview.startCoroutine()
                flipflop = false
                intentx = Intent(this,  NewServicex::class.java)
                intentx.putExtra("samplename", "abd")
                startService(intentx)
            }
            else
            {flipflop = true
                Aview.clearJob()
                counter=0
                text_.setText(counter.toString())
                val ss:String = intentx.getStringExtra("samplename").toString()
                stopService(intentx)
                val ss2:String = intentx.getStringExtra("samplename").toString()
                var stoppaqui=0
            }
        }

    }


    private fun createAdapter(): ViewPagerAdapter? {
        return ViewPagerAdapter(this)
    }


    inner class AndroidViewModel() : ViewModel() {

        val parentJob = Job()
        val coroutineScope = CoroutineScope(parentJob + Dispatchers.Default)
        var stopstart=false
        fun startCoroutine() {
            stopstart=true
            val job = coroutineScope.launch {

                while(stopstart) {
                    Thread.sleep(100)
                    counter++




                    viewModelScope.launch(Dispatchers.Main){
                        var pippo=0;
                        text_.setText(counter.toString())

                       /* final String sender=this.getIntent.getExtras().getString("SENDER_KEY");

                        //IF ITS THE FRAGMENT THEN RECEIVE DATA
                        if(sender != null)
                        {
                            this.receiveData()

                        }*/

                }

                }
            }
        }

        fun clearJob(){
            stopstart=false

        }

        override fun onCleared() {
            super.onCleared()
            this.parentJob.cancel()
        }

    }






public  class UHFMainActivity : AppCompatActivity() {
    fun sendData(`object`: Any?) {
//        todo do your stuff with object
    }
}

    public class NewService : Service() {
        // declaring object of MediaPlayer
        private var player: MediaPlayer? = null

        // execution of service will start
        // on calling this method
        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

            // creating a media player which
            // will play the audio of Default
            // ringtone in android device
            player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)

            // providing the boolean
            // value as true to play
            // the audio on loop
            player!!.isLooping = true

            // starting the process
            player!!.start()

            // returns the status
            // of the program
            return START_STICKY
        }

        // execution of the service will
        // stop on calling this method
        override fun onDestroy() {
            super.onDestroy()

            // stopping the process
            player!!.stop()
        }

        @Nullable
        override fun onBind(intent: Intent?): IBinder? {
            return null
        }
    }


}


public class NewServicex : Service() {
    // declaring object of MediaPlayer
    private var player: MediaPlayer? = null

    // execution of service will start
    // on calling this method
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        // creating a media player which
        // will play the audio of Default
        // ringtone in android device
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)

        // providing the boolean
        // value as true to play
        // the audio on loop
        player!!.isLooping = true

        // starting the process
        player!!.start()

        // returns the status
        // of the program
        return START_STICKY
    }

    // execution of the service will
    // stop on calling this method
    override fun onDestroy() {
        super.onDestroy()

        // stopping the process
        player!!.stop()
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}

