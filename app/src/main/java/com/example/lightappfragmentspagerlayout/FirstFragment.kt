package com.example.lightappfragmentspagerlayout

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var layoutInflater_view: View
    lateinit var button_AP:Button
    lateinit var button_Devices:Button
    lateinit var probar:ProgressBar


    lateinit var thiscontext: Context
    lateinit var rv: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    private fun setProgressValue(progress:Int) {

        // set the progress
        probar.setProgress(progress);

        Thread(Runnable {
            while (progress<600) {
                try {
                    Thread.sleep(500) //500ms delay
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                setProgressValue(progress + 2);
            }
        }).start()

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        thiscontext = (activity as MainActivity?)?.activity_!!.applicationContext

        layoutInflater_view=inflater.inflate(R.layout.fragment_first, container, false)
        return  (layoutInflater_view)
    }

    @SuppressLint("WrongViewCast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(activity as MainActivity?)?.setdata(value)


        button_AP = layoutInflater_view.findViewById<View>(R.id.Change_Wifi_AccessPoint) as Button
        button_Devices = layoutInflater_view.findViewById<View>(R.id.Scan_for_Devices) as Button
        probar= layoutInflater_view.findViewById<View>(R.id.progressBar) as ProgressBar

        probar.setVisibility(View.INVISIBLE);
        (activity as MainActivity?)?.broadcaster_receiver = UDPBroadcaster(thiscontext)



        button_AP.setOnClickListener {


            /*val myIntent = Intent(this@MainActivity, scan_wifi::class.java)
            this@MainActivity.startActivity(myIntent)*/

        }
        button_Devices.setOnClickListener {

            var progre=0
            probar.setMax(100)
            probar.setProgress(0)
            probar.setVisibility(View.VISIBLE);
            var siz:Int=0

            (activity as MainActivity).broadcaster_receiver.recvUDPBroadcast()



            Thread(Runnable {

                while (progre < 100) {
                    Thread.sleep(50)
                    probar.setProgress(progre);
                    progre += 1
                }
                probar.setVisibility(View.INVISIBLE);
            }).start()

            
            Handler().postDelayed({
                (activity as MainActivity).listofdevices = (activity as MainActivity).broadcaster_receiver.close()



                if ((activity as MainActivity).listofdevices != null) {


                    //light_characteristics = mutableListOf()
                    (activity as MainActivity).listofsender = mutableListOf()


                    siz = (activity as MainActivity).listofdevices?.size as Int
                    if (siz > 0) {

                        var Type_: ArrayList<String> = ArrayList<String>(siz)
                        var Title_: ArrayList<String> = ArrayList<String>(siz)
                        var imgid_dots: ArrayList<Int> = ArrayList<Int>(siz)
                        var imgid_edit: ArrayList<Int> = ArrayList<Int>(siz)
                        var imgid_on: ArrayList<Int> = ArrayList<Int>(siz)

                        for (u in 0..siz - 1) {
                            Type_.add(u.toString())
                            var temptitle: MutableList<String> = (activity as MainActivity).listofdevices!!.get(u)
                            Title_.add(temptitle.get(0) as String)
                            imgid_dots.add(R.drawable.ic_audiotrack_24px)
                            imgid_edit.add(R.drawable.ic_create_24px)
                            imgid_on.add(R.drawable.ic_settings_power_24px)

                            (activity as MainActivity).light_characteristics.add(lightinformation(NameofAP = temptitle?.get(0) as String))
                            (activity as MainActivity).light_characteristics.get(u).IP = temptitle.get(1) as String
                            (activity as MainActivity).light_characteristics.get(u).port = temptitle?.get(2).toInt() as Int

                            (activity as MainActivity).listofsender.add(UDPBroadcaster(thiscontext))

                        }



                        rv = layoutInflater_view.findViewById<View>(R.id.RecView_devices) as RecyclerView
                        // Initialize contacts
                        // Create adapter passing in the sample user data


                        val adapter = ContactsAdapter(
                                Type_.toTypedArray(),
                                Title_.toTypedArray(),
                                imgid_dots.toTypedArray(),
                                imgid_edit.toTypedArray(),
                                imgid_on.toTypedArray(),
                                (activity as MainActivity).light_characteristics,
                                (activity as MainActivity).listofsender,
                                (activity as MainActivity).nn
                        )


                        // Attach the adapter to the recyclerview to populate items
                        rv.adapter = adapter
                        // Set layout manager to position the items
                        rv.layoutManager = LinearLayoutManager(thiscontext)


                    }
                }
                }, 5000)



            //setVisibility(View.VISIBLE);


        }


    }
}