package com.example.lightappfragmentspagerlayout

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import android.graphics.drawable.Drawable

import android.graphics.drawable.ColorDrawable

import android.graphics.drawable.GradientDrawable

import android.graphics.drawable.ShapeDrawable








//https://www.baeldung.com/kotlin/threads-coroutines

class SecondFragment : Fragment() {

    lateinit var spin_: Spinner
    lateinit var myCanvasView:MyCanvasView
    lateinit var imageView_symbol:ImageView
    lateinit var warmwhite: TextView
    lateinit var brightness: TextView
    lateinit var seekBar_white: SeekBar
    lateinit var seekBar_brightness: SeekBar
    lateinit var seekBar_H: SeekBar
    lateinit var Htext: TextView
    lateinit var  b_arrow: ImageView
    var deactivatedraw:Int=0

    lateinit var Nameoflight:TextView
    lateinit var cbutton0:Button
    lateinit var cbutton1:Button
    lateinit var cbutton2:Button
    lateinit var cbutton3:Button


    lateinit var thiscontext: Context

    var buttonstatus:UInt=0u




    lateinit var layoutInflater_view: View
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        layoutInflater_view= inflater.inflate(R.layout.fragment_second, container, false)
        return layoutInflater_view
    }


    public fun changeseekbarwhite(progress: Int)
    {
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


    public fun changeseekbarbrigthness(progress: Int)
    {
        var stringa: String = progress.toString()
        if (progress < 10)
            stringa = "  " + stringa
        else
            if ((progress < 100) and (progress >= 10))
                stringa = " " + stringa
        brightness.setText("Brightness " + stringa + "%")
        seekBar_brightness.progress=progress
    }

    public fun changeseekbarH(progress: Int)
    {
        var stringa: String = progress.toString()
        if (progress < 10)
            stringa = "  " + stringa
        else
            if ((progress < 100) and (progress >= 10))
                stringa = " " + stringa
        Htext.setText("H level    " + stringa + "%")
        seekBar_H.progress=progress
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        thiscontext = (activity as MainActivity?)?.activity_!!.applicationContext


        myCanvasView = layoutInflater_view.findViewById(R.id.imageView_)
        imageView_symbol= layoutInflater_view.findViewById(R.id.imageView_symbol)
        warmwhite= layoutInflater_view.findViewById(R.id.warmwhite)
        brightness= layoutInflater_view.findViewById(R.id.brigthness)
        seekBar_white= layoutInflater_view.findViewById(R.id.seekBar_white)
        seekBar_brightness= layoutInflater_view.findViewById(R.id.seekBar2)
        Nameoflight= layoutInflater_view.findViewById(R.id.Nameoflight)
        seekBar_H=layoutInflater_view.findViewById(R.id.seekBar_H)
        Htext=layoutInflater_view.findViewById(R.id.Hseekbar)

        b_arrow = layoutInflater_view.findViewById<TextView>(R.id.imageView_back) as ImageView


        imageView_symbol.setImageResource(R.mipmap.moobiled_foreground)


        cbutton0= layoutInflater_view.findViewById(R.id.colorbutton)
        cbutton1= layoutInflater_view.findViewById(R.id.colorbutton1)
        cbutton2= layoutInflater_view.findViewById(R.id.colorbutton2)
        cbutton3= layoutInflater_view.findViewById(R.id.colorbutton3)



        cbutton0.setOnClickListener {
            if ((buttonstatus and 0x01u)>0u) {
                buttonstatus = 0x00u
            }
            else{


                buttonstatus = (buttonstatus and 0x00u) or 1u//shl 24
            }
            setupbutton(0,0u)
        }

        cbutton1.setOnClickListener {
            if ((buttonstatus  and 0x02u)>0u) {
                buttonstatus = 0x00u
            }
            else{
                buttonstatus =  2u//shl 24
            }
            setupbutton(0,1u)
        }


        cbutton2.setOnClickListener {
            if ((buttonstatus  and 0x04u)>0u) {

                buttonstatus = 0x00u
            }
            else{

                buttonstatus =  4u//shl 24
            }
            setupbutton(0,2u)
        }

        cbutton3.setOnClickListener {
            if ((buttonstatus  and 0x08u)>0u) {
                buttonstatus =  0x00u//shl 24
            }
            else{
                buttonstatus =  8u//shl 24
            }
            setupbutton(0,3u)
        }



        /* spin_= layoutInflater_view.findViewById<TextView>(R.id.spinner) as Spinner
         ArrayAdapter.createFromResource(
                 (activity as MainActivity),
                 R.array.programs_array,
                 android.R.layout.simple_spinner_item
         ).also { adapter ->
             // Specify the layout to use when the list of choices appears
             adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
             // Apply the adapter to the spinner
             spin_.adapter = adapter
         }*/

       /* spin_.setOnItemSelectedListener(object : OnItemSelectedListener {
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
        }) */


        b_arrow.setOnClickListener {
            backsecondfragment()
        }


        seekBar_H.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
                var  hsv = FloatArray(3)

                hsv[0]=360f *progress.toFloat()/100f
                hsv[1]=myCanvasView.S
                hsv[2]=myCanvasView.V
                myCanvasView.modifiedcolor= Color.HSVToColor(hsv);

                hsv[0]=360f *progress.toFloat()/100f
                hsv[1]=1.0f
                hsv[2]=1.0f
                myCanvasView.unmodifiedcolor= Color.HSVToColor(hsv);
                Htext.setText("H level    " + stringa + "%")
                myCanvasView.drawstuff()

               /* if ((buttonstatus  and 0x08u)>0u){

                    val background: Drawable = cbutton3.getBackground()
                    if (background is ShapeDrawable) {
                        // cast to 'ShapeDrawable'
                        background.paint.color = myCanvasView.modifiedcolor
                    } else if (background is GradientDrawable) {
                        // cast to 'GradientDrawable'
                        background.setColor(myCanvasView.modifiedcolor)
                    } else if (background is ColorDrawable) {
                        // alpha value may need to be set again after this call
                        background.color = myCanvasView.modifiedcolor
                    }

                }*/

                setupbutton(myCanvasView.modifiedcolor, 5u)


            }
            override fun onStartTrackingTouch(arg0: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }
        })

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
                //myCanvasView.Sint = progress
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




    }


    public fun setupbutton(colotoset:Int, buttonpressed:UInt){

        var background_: Drawable

        if ((buttonstatus and 0x01u)>0u)
        {

            cbutton0.setBackgroundResource(R.drawable.button_border_0)
            background_ = cbutton0.getBackground()

            if (buttonpressed!=0u) {
                (background_ as GradientDrawable).setColor(colotoset)
            }
            cbutton0.setBackgroundResource(R.drawable.button_border_0_t)
                //(background0 as GradientDrawable).setStroke(dpToPx(3),0xFFFFFF)
            background_ = cbutton0.getBackground()
            if (buttonpressed!=0u) {
            (background_ as GradientDrawable).setColor(colotoset)
            }

        }
        else
        {
            cbutton0.setBackgroundResource(R.drawable.button_border_0)
        }


        if ((buttonstatus and 0x02u)>0u)
        {
            cbutton1.setBackgroundResource(R.drawable.button_border_1)
            background_ = cbutton1.getBackground()

            if (buttonpressed!=1u) {
                (background_ as GradientDrawable).setColor(colotoset)
            }
            cbutton1.setBackgroundResource(R.drawable.button_border_1_t)
            //(background0 as GradientDrawable).setStroke(dpToPx(3),0xFFFFFF)
            background_ = cbutton1.getBackground()
            if (buttonpressed!=1u) {
                (background_ as GradientDrawable).setColor(colotoset)
            }

        }
        else
        {
            cbutton1.setBackgroundResource(R.drawable.button_border_1)
        }


        if ((buttonstatus and 0x04u)>0u)
        {
            cbutton2.setBackgroundResource(R.drawable.button_border_2)
            background_ = cbutton2.getBackground()

            if (buttonpressed!=2u) {
                (background_ as GradientDrawable).setColor(colotoset)
            }
            cbutton2.setBackgroundResource(R.drawable.button_border_2_t)
            //(background0 as GradientDrawable).setStroke(dpToPx(3),0xFFFFFF)
            background_ = cbutton2.getBackground()
            if (buttonpressed!=2u) {
                (background_ as GradientDrawable).setColor(colotoset)
            }

        }
        else
        {
            cbutton2.setBackgroundResource(R.drawable.button_border_2)
        }



        if ((buttonstatus and 0x08u)>0u)
        {
            cbutton3.setBackgroundResource(R.drawable.button_border_3)
            background_ = cbutton3.getBackground()

            if (buttonpressed!=3u) {
                (background_ as GradientDrawable).setColor(colotoset)
            }
            cbutton3.setBackgroundResource(R.drawable.button_border_3_t)
            //(background0 as GradientDrawable).setStroke(dpToPx(3),0xFFFFFF)
            background_ = cbutton3.getBackground()
            if (buttonpressed!=3u) {
                (background_ as GradientDrawable).setColor(colotoset)
            }

        }
        else
        {
            cbutton3.setBackgroundResource(R.drawable.button_border_3)
        }

    }

    fun dpToPx(dps: Int): Int {
        return Math.round(resources.displayMetrics.density * dps)
    }

    public fun backsecondfragment() {
        (activity as MainActivity).vpPager.setCurrentItem(0)
        val myFragment = (activity as MainActivity).supportFragmentManager.findFragmentByTag("f0")
        (myFragment as FirstFragment).rv.adapter?.notifyItemChanged((activity as MainActivity).nn.n)
        (activity as MainActivity).image_start.setVisibility(View.VISIBLE)
    }

}