package com.example.lightappfragmentspagerlayout

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.slider.Slider


//https://www.baeldung.com/kotlin/threads-coroutines

class SecondFragment : Fragment() {

    lateinit var slider: Slider
    lateinit var layoutInflater_view: View
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layoutInflater_view= inflater.inflate(R.layout.fragment_second, container, false)
        return layoutInflater_view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        slider= layoutInflater_view.findViewById(R.id.slider_)

        slider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
                // Responds to when slider's touch event is being started

                val value = slider.value.toInt() // or just your string
                (activity as MainActivity?)?.setdata(value)
                if (false) {
                    val intent = Intent(getActivity()?.getBaseContext(), MainActivity.UHFMainActivity::class.java) as Intent
                    intent.putExtra("value", value)
                    startActivity(intent)
                }
            }

            override fun onStopTrackingTouch(slider: Slider) {
                // Responds to when slider's touch event is being stopped
            }
        })

        slider.addOnChangeListener { slider, value, fromUser ->
            // Responds to when slider's value is changed

          //  val intent = Intent(getActivity()?.getBaseContext(), MainActivity::class.java)
          //  intent.putExtra("samplename", "abd")
           // startActivity(intent)

           if (false) {
               val context = requireActivity()
               /*getActivity() ? . getBaseContext()*/
               val intent = Intent(context, NewServicex::class.java)
               //intent.putExtra("samplename", "abd")
               //getActivity()?.getBaseContext()?.startService(intent)

               context.startService(intent)
               val ss: String = intent.getStringExtra("samplename").toString()

           }
        }

    }

}