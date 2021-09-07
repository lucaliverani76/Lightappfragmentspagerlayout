package com.example.lightappfragmentspagerlayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.fragment.app.Fragment


//https://www.baeldung.com/kotlin/threads-coroutines

class SecondFragment : Fragment() {

    lateinit var spin_: Spinner
    lateinit var myCanvasView:MyCanvasView
    lateinit var warmwhite: TextView
    lateinit var brightness: TextView
    lateinit var seekBar_white: SeekBar
    lateinit var seekBar_brightness: SeekBar
    lateinit var  b_arrow: ImageView
    var deactivatedraw:Int=0




    lateinit var layoutInflater_view: View
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layoutInflater_view= inflater.inflate(R.layout.fragment_second, container, false)
        return layoutInflater_view
    }


    public fun changeseekbarwhite(progress: Int){
        val progressx=progress
        var stringa: String = progressx.toString()
        if (progressx < 10)
            stringa = "  " + stringa
        else
            if ((progressx < 100) and (progressx >= 10))
                stringa = " " + stringa
        warmwhite.setText("Warm white " + stringa + "%")

        seekBar_white.progress=progressx


    }


    public fun changeseekbarbrigthness(progress: Int){
        var stringa: String = progress.toString()
        if (progress < 10)
            stringa = "  " + stringa
        else
            if ((progress < 100) and (progress >= 10))
                stringa = " " + stringa
        brightness.setText("Brightness " + stringa + "%")
        seekBar_brightness.progress=progress
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //(activity as MainActivity?)?.setdata(value)



        myCanvasView = layoutInflater_view.findViewById(R.id.imageView_)

        warmwhite= layoutInflater_view.findViewById(R.id.warmwhite)
        brightness= layoutInflater_view.findViewById(R.id.brigthness)
        seekBar_white= layoutInflater_view.findViewById(R.id.seekBar_white)
        seekBar_brightness= layoutInflater_view.findViewById(R.id.seekBar2)


        b_arrow = layoutInflater_view.findViewById<TextView>(R.id.imageView_back) as ImageView

        spin_= layoutInflater_view.findViewById<TextView>(R.id.spinner) as Spinner
        ArrayAdapter.createFromResource(
                (activity as MainActivity),
                R.array.programs_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spin_.adapter = adapter
        }

        spin_.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {


                if ((activity as MainActivity).light_characteristics!=null  && (activity as MainActivity).light_characteristics.size>0)
                {(activity as MainActivity).setdata(specialcommand = position)}


                if (position==0){
                    seekBar_white.setEnabled(true)
                    seekBar_brightness.setEnabled(true)
                    myCanvasView.setVisibility(View.VISIBLE)
                }
                else
                {
                    seekBar_white.setEnabled(false)
                    seekBar_brightness.setEnabled(false)
                    myCanvasView.setVisibility(View.INVISIBLE)

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        })



        b_arrow.setOnClickListener {
            (activity as MainActivity).vpPager.setCurrentItem(0)
            val myFragment = (activity as MainActivity).supportFragmentManager.findFragmentByTag("f0")
            (myFragment as FirstFragment).rv.adapter?.notifyItemChanged((activity as MainActivity).nn.n)
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
                myCanvasView.Sint = progress
                myCanvasView.White_=kotlin.math.min(255f*progress.toFloat()/100f, 255f).toInt()
                if (deactivatedraw == 0) {
                    myCanvasView.drawstuff()
                }
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
                myCanvasView.Vint = progress
                if (deactivatedraw == 0) {
                    myCanvasView.drawstuff()
                }
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