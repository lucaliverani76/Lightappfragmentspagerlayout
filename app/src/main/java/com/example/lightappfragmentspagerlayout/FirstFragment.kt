package com.example.lightappfragmentspagerlayout

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


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

    lateinit var APtext:TextView
    lateinit var Ptext:TextView
    lateinit var PAssText:EditText

    lateinit var Buttonconnect:Button
    lateinit var Buttoncancel:Button

    lateinit var imageView_symbol:ImageView

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

        imageView_symbol= layoutInflater_view.findViewById(R.id.imageView_symbol2)

        button_Devices = layoutInflater_view.findViewById<View>(R.id.Scan_for_Devices) as Button
        probar= layoutInflater_view.findViewById<View>(R.id.progressBar) as ProgressBar

        APtext= layoutInflater_view.findViewById<View>(R.id.textView) as TextView
        Ptext= layoutInflater_view.findViewById<View>(R.id.textView4) as TextView
        PAssText= layoutInflater_view.findViewById<View>(R.id.editTextTextPassword) as EditText
        Buttonconnect= layoutInflater_view.findViewById<View>(R.id.button) as Button
        Buttoncancel= layoutInflater_view.findViewById<View>(R.id.button2) as Button

        imageView_symbol.setImageResource(R.mipmap.moobiled_foreground)

        APtext.setVisibility(View.INVISIBLE);
        Ptext.setVisibility(View.INVISIBLE);
        PAssText.setVisibility(View.INVISIBLE);
        Buttonconnect.setVisibility(View.INVISIBLE);
        Buttoncancel.setVisibility(View.INVISIBLE);

        probar.setVisibility(View.INVISIBLE);
        (activity as MainActivity?)?.broadcaster_receiver = UDPBroadcaster(thiscontext)

        if ((activity as MainActivity).light_characteristics.size>0) {
            rv = layoutInflater_view.findViewById<View>(R.id.RecView_devices) as RecyclerView
            // Initialize contacts
            // Create adapter passing in the sample user data


            val adapter = ContactsAdapter(
                    /*Type_.toTypedArray(),
                Title_.toTypedArray(),
                imgid_dots.toTypedArray(),
                imgid_edit.toTypedArray(),
                imgid_on.toTypedArray(),*/
                    (activity as MainActivity).light_characteristics,
                    /*(activity as MainActivity).listofsender,*/
                    (activity as MainActivity).nn
            )


            // Attach the adapter to the recyclerview to populate items
            rv.adapter = adapter
            // Set layout manager to position the items
            rv.layoutManager = LinearLayoutManager(thiscontext)
        }


        Buttonconnect.setOnClickListener {
            (activity as MainActivity).Scanwifi.wifiSSID= (APtext.getText() as String).replace("AP Name: " ,"")
            (activity as MainActivity).Scanwifi.wifiPassword= PAssText.getText().toString()

            (activity as MainActivity).Scanwifi.tryconnection()

            Handler().postDelayed({
                APtext.setVisibility(View.INVISIBLE);
                Ptext.setVisibility(View.INVISIBLE);
                PAssText.setVisibility(View.INVISIBLE);
                Buttonconnect.setVisibility(View.INVISIBLE);
                Buttoncancel.setVisibility(View.INVISIBLE);
                button_Devices.callOnClick()
            }, 5000)
            }


        Buttoncancel.setOnClickListener {
            APtext.setVisibility(View.INVISIBLE);
            Ptext.setVisibility(View.INVISIBLE);
            PAssText.setVisibility(View.INVISIBLE);
            Buttonconnect.setVisibility(View.INVISIBLE);
            Buttoncancel.setVisibility(View.INVISIBLE);
        }


        button_Devices.setOnClickListener {

            var progre=0
            probar.setMax(100)
            probar.setProgress(0)
            probar.setVisibility(View.VISIBLE);

            var siz_devices:Int=0
            var siz_AP:Int=0

            (activity as MainActivity).broadcaster_receiver.recvUDPBroadcast()
            (activity as MainActivity).Scanwifi.startScanning()

            if ((activity as MainActivity).light_characteristicsglobal!=null  && (activity as MainActivity).light_characteristicsglobal!!.size>0 )
            for (j in 0..(activity as MainActivity).light_characteristicsglobal!!.size-1)
            {(activity as MainActivity).light_characteristicsglobal.get(j).closeUDP()}

            Thread(Runnable
            {

                while (progre < 100)
                {
                    Thread.sleep(50)
                    probar.setProgress(progre);
                    progre += 1
                }
                probar.setVisibility(View.INVISIBLE);
            }).start()


            Handler().postDelayed({

                //(activity as MainActivity).listofsender = mutableListOf()
                (activity as MainActivity).light_characteristicsglobal = mutableListOf()

                (activity as MainActivity).listofdevices = (activity as MainActivity).broadcaster_receiver.close()

                if ((activity as MainActivity).listofdevices != null)
                {
                    siz_devices = (activity as MainActivity).listofdevices?.size as Int
                }

                if ( (activity as MainActivity).Scanwifi.scandone==true)
                {
                    siz_AP = (activity as MainActivity).Scanwifi.list?.size as Int
                }

                if ((siz_devices + siz_AP) > 0)
                {

                    /*var Type_: ArrayList<String> = ArrayList<String>(siz_devices + siz_AP)
                    var Title_: ArrayList<String> = ArrayList<String>(siz_devices + siz_AP)
                    var imgid_dots: ArrayList<Int> = ArrayList<Int>(siz_devices + siz_AP)
                    var imgid_edit: ArrayList<Int> = ArrayList<Int>(siz_devices + siz_AP)
                    var imgid_on: ArrayList<Int> = ArrayList<Int>(siz_devices + siz_AP)*/




                    if (siz_devices>0){
                        for (u in 0..siz_devices - 1)
                        {
                           /* Type_.add("Light")*/
                            var temptitle: MutableList<String> = (activity as MainActivity).listofdevices!!.get(u)
                            /*Title_.add(temptitle.get(0) as String)
                            imgid_dots.add(R.drawable.ic_audiotrack_24px)
                            imgid_edit.add(R.drawable.ic_baseline_color_lens_24)
                            imgid_on.add(R.drawable.ic_baseline_toggle_off_24)*/

                            (activity as MainActivity).light_characteristicsglobal.add(lightinformation(NameofAP = temptitle?.get(0) ))
                            (activity as MainActivity).light_characteristicsglobal.get(u).IP = temptitle.get(1)
                            (activity as MainActivity).light_characteristicsglobal.get(u).port = temptitle?.get(2).toInt()
                            (activity as MainActivity).light_characteristicsglobal.get(u).nameofWifi = temptitle?.get(3)
                            (activity as MainActivity).light_characteristicsglobal.get(u).battery = temptitle?.get(4)


                            (activity as MainActivity).light_characteristicsglobal.get(u).udpbroadcaster=UDPBroadcaster(thiscontext)

                            (activity as MainActivity).light_characteristicsglobal.get(u).udpbroadcaster?.open(
                                localPort = 10000 + (activity as MainActivity).light_characteristicsglobal.get(u).portidentified,
                                destPort = (activity as MainActivity).light_characteristicsglobal.get(u).port,
                                _IP = (activity as MainActivity).light_characteristicsglobal.get(u).IP,
                                lIGHT = (activity as MainActivity).light_characteristicsglobal.get(u)
                            )


                            (activity as MainActivity).light_characteristicsglobal.get(u).Type_="Light"
                            (activity as MainActivity).light_characteristicsglobal.get(u).hsv=(activity as MainActivity).hsv_music
                        }
                    }

                    if (siz_AP>0){
                        for (u in siz_devices..(siz_AP+siz_devices - 1))
                        {
                            /*Type_.add("AP")*/
                            var temptitle: List<String> = (activity as MainActivity).Scanwifi.list
                            /*Title_.add(temptitle.get(0) )
                            imgid_dots.add(R.drawable.ic_baseline_info_24)
                            imgid_edit.add(R.drawable.ic_baseline_import_export_24)
                            imgid_on.add(R.mipmap.empty_round)*/

                            (activity as MainActivity).light_characteristicsglobal.add(lightinformation(NameofAP = temptitle?.get(0) ))
                            (activity as MainActivity).light_characteristicsglobal.get(u).IP = "0.0.0.0"
                            (activity as MainActivity).light_characteristicsglobal.get(u).port = 0

                            (activity as MainActivity).light_characteristicsglobal.get(u).udpbroadcaster=UDPBroadcaster(thiscontext)
                            (activity as MainActivity).light_characteristicsglobal.get(u).Type_="AP"
                        }
                    }


                    (activity as MainActivity).light_characteristics= (activity as MainActivity).light_characteristicsglobal
                    rv = layoutInflater_view.findViewById<View>(R.id.RecView_devices) as RecyclerView
                    // Initialize contacts
                    // Create adapter passing in the sample user data


                    val adapter = ContactsAdapter(
                            /*Type_.toTypedArray(),
                            Title_.toTypedArray(),
                            imgid_dots.toTypedArray(),
                            imgid_edit.toTypedArray(),
                            imgid_on.toTypedArray(),*/
                            (activity as MainActivity).light_characteristics,
                            /*(activity as MainActivity).listofsender,*/
                            (activity as MainActivity).nn
                    )


                    // Attach the adapter to the recyclerview to populate items
                    rv.adapter = adapter
                    // Set layout manager to position the items
                    rv.layoutManager = LinearLayoutManager(thiscontext)


                }
            }, 5000)
            


        }


    }
}