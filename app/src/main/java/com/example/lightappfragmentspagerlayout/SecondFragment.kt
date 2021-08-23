package com.example.lightappfragmentspagerlayout

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.slider.Slider


//https://www.baeldung.com/kotlin/threads-coroutines

class SecondFragment : Fragment() {


    lateinit var myCanvasView:MyCanvasView
    lateinit var warmwhite: TextView
    lateinit var brightness: TextView
    lateinit var seekBar_white: SeekBar
    lateinit var seekBar_brightness: SeekBar
    lateinit var  b_arrow: TextView

    lateinit var layoutInflater_view: View


    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layoutInflater_view= inflater.inflate(R.layout.fragment_second, container, false)
        return layoutInflater_view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(activity as MainActivity?)?.setdata(value)


        myCanvasView = layoutInflater_view.findViewById(R.id.imageView_)

        warmwhite= layoutInflater_view.findViewById(R.id.warmwhite)
        brightness= layoutInflater_view.findViewById(R.id.brigthness)
        seekBar_white= layoutInflater_view.findViewById(R.id.seekBar_white)
        seekBar_brightness= layoutInflater_view.findViewById(R.id.seekBar2)
        b_arrow = layoutInflater_view.findViewById<TextView>(R.id.backarrow)

        b_arrow.setOnClickListener {

            (activity as MainActivity).showFragment(0)

        }

        seekBar_white.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                    seekBar: SeekBar, progress: Int,
                    fromUser: Boolean
            ) {
                var stringa: String = progress.toString()
                if (progress < 10)
                    stringa = "  " + stringa
                else
                    if ((progress < 100) and (progress >= 10))
                        stringa = " " + stringa
                warmwhite.setText("Warm white " + stringa + "%")
                myCanvasView.V = progress.toFloat() / 100f
                myCanvasView.drawstuff()
            }

            override fun onStartTrackingTouch(arg0: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }
        })

        seekBar_brightness.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                    seekBar: SeekBar, progress: Int,
                    fromUser: Boolean
            ) {
                var stringa: String = progress.toString()
                if (progress < 10)
                    stringa = "  " + stringa
                else
                    if ((progress < 100) and (progress >= 10))
                        stringa = " " + stringa
                brightness.setText("Brightness " + stringa + "%")
                myCanvasView.S = progress.toFloat() / 100f
                myCanvasView.drawstuff()
            }

            override fun onStartTrackingTouch(arg0: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }
        })
        //myCanvasView.setImageBitmap(myCanvasView.extraBitmap);

    }

}