package com.example.lightappfragmentspagerlayout

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Groupview_modes(
        var modes: Array<String>
) : RecyclerView.Adapter<Groupview_modes.ViewHolder>() {

    lateinit var context: Context
    var nowclicked:Int=0


   /* override fun getItemViewType(position: Int): Int {

    }*/

    // Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
    inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {

        val textView_element = listItemView.findViewById(R.id.textView3) as TextView
        val checkb = listItemView.findViewById(R.id.checkBox) as CheckBox
        val image_edit = listItemView.findViewById(R.id.imageView_edit) as ImageView



    }

    // ... constructor and member variables
// Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
// Inflate the custom layout
        var contactView:View

        contactView = inflater.inflate(R.layout.grouplayout_modes, parent, false)


// Return a new holder instance

        var viewHolder= ViewHolder(contactView)

        return viewHolder
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.textView_element.text = modes.get(position) //Type_[position]

        viewHolder.checkb.isChecked = false
        if (position==0)
        {
            var n=(context as MainActivity).nn.n
            val unwrappedDrawable = AppCompatResources.getDrawable(context, R.drawable.ic_baseline_color_lens_24)
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            var colortoapply:Int=( 0xff shl 24) or ( 0xff shl 16) or  (0xff shl 8) or (0xff)
            if ((context as MainActivity).light_characteristicsglobal.get(n).modifiedcolor != 0)
            {colortoapply=(context as MainActivity).light_characteristicsglobal.get(n).modifiedcolor}

            DrawableCompat.setTint(wrappedDrawable, colortoapply )
            viewHolder.image_edit.setImageResource(R.drawable.ic_baseline_color_lens_24)

        }
        else {
            if (position == 1) {
                viewHolder.image_edit.setImageResource(R.drawable.ic_launcher_music_x)
             /*   val background: Drawable = cbutton3.getBackground()
                if (background is ShapeDrawable) {
                    // cast to 'ShapeDrawable'
                    background.paint.color = myCanvasView.modifiedcolor}*/

            } else {
                viewHolder.image_edit.setImageResource(0)
            }
        }


        if(nowclicked==position){
            viewHolder.checkb.isChecked=true
        }else {
            viewHolder.checkb.isChecked = false
        }


        viewHolder.checkb.setOnClickListener()
        {
            nowclicked = viewHolder.getAdapterPosition();
            val myFragment = (context as MainActivity).supportFragmentManager.findFragmentByTag("f3")
            (myFragment as Settingsap).updatestuff(actionmode_ = nowclicked)
            notifyDataSetChanged();
        }

}






    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return modes.size
    }
}


/**
 * A simple [Fragment] subclass.
 * Use the [Settingsap.newInstance] factory method to
 * create an instance of this fragment.
 */
class Settingsap : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var rv: RecyclerView

    lateinit var layoutInflater_view: View
    lateinit var thiscontext: Context
    lateinit var imageView_symbol:ImageView
    lateinit var imageView_battery:ImageView
    lateinit var imageView_wifi:ImageView
    lateinit var editnamelight :EditText
    lateinit var speed_text :TextView

    lateinit var connectname :TextView

    lateinit var editbattery :TextView

    lateinit var seekBar_speed: SeekBar

    lateinit var b_arrow: ImageView


    public fun changeseekbarspeed(progress: Int)
    {
        var stringa: String = progress.toString()
        if (progress < 10)
            stringa = "  " + stringa
        else
            if ((progress < 100) and (progress >= 10))
                stringa = " " + stringa
        speed_text.setText("Speed " + stringa + "%")
        seekBar_speed.progress=progress
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {


        thiscontext = (activity as MainActivity?)?.activity_!!.applicationContext

        layoutInflater_view=inflater.inflate(R.layout.fragment_settingsap, container, false)
        return  (layoutInflater_view)
    }





    @SuppressLint("WrongViewCast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView_symbol = layoutInflater_view.findViewById(R.id.imageView_symbol3)
        imageView_symbol.setImageResource(R.mipmap.moobiled_foreground)

        editnamelight = layoutInflater_view.findViewById(R.id.editLightName)
        imageView_battery = layoutInflater_view.findViewById(R.id.battery)
        imageView_wifi = layoutInflater_view.findViewById(R.id.wifi)
        imageView_battery.setImageResource(R.drawable.ic_baseline_battery_charging_full_24)
        imageView_wifi.setImageResource(R.drawable.ic_baseline_wifi_24)
        speed_text = layoutInflater_view.findViewById(R.id.speedtext)
        seekBar_speed = layoutInflater_view.findViewById(R.id.seekBar3)

        connectname= layoutInflater_view.findViewById(R.id.connectName)

        editbattery= layoutInflater_view.findViewById(R.id.editbattery)


        b_arrow = layoutInflater_view.findViewById(R.id.imageView_back2) as ImageView


        rv = layoutInflater_view.findViewById<View>(R.id.recview_modes) as RecyclerView

        val adapter = Groupview_modes(getResources().getStringArray(R.array.programs_array))

        updatestuff()

// Attach the adapter to the recyclerview to populate items
        rv.adapter = adapter
// Set layout manager to position the items
        rv.layoutManager = LinearLayoutManager(thiscontext)

        b_arrow.setOnClickListener {
            backsettingsfragment()
        }

        seekBar_speed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
                speed_text.setText("Speed " + stringa + "%")

                updatestuff(speed_=progress)
            }
                 override fun onStartTrackingTouch(arg0: SeekBar) {
                     // TODO Auto-generated method stub
                 }

                 override fun onStopTrackingTouch(seekBar: SeekBar) {
                     // TODO Auto-generated method stub
                 }
             })

    }

    public fun updatestuff(actionmode_:Int=-1,speed_:Int=-1 ){


        var n = (activity as MainActivity).nn.n
        (activity as MainActivity).light_characteristics.get(n).NameofAP
        editnamelight.setText((activity as MainActivity).light_characteristics.get(n).NameofAP)

        connectname.setText((activity as MainActivity).light_characteristics.get(n).nameofWifi)
        editbattery.setText((activity as MainActivity).light_characteristics.get(n).battery + "%")

        var colortodraw =(activity as MainActivity).light_characteristics.get(n).modifiedcolor
        var redValue_m = (activity as MainActivity).light_characteristics.get(n).red
        var greenValue_m = (activity as MainActivity).light_characteristics.get(n).green
        var blueValue_m= (activity as MainActivity).light_characteristics.get(n).blue
        var white_m= (activity as MainActivity).light_characteristics.get(n).white
        var Sint= (activity as MainActivity).light_characteristics.get(n).Sint
        var Vint= (activity as MainActivity).light_characteristics.get(n).Vint
        var actiomode=0
        var speed=0
        if (actionmode_==-1) {
            actiomode = (activity as MainActivity).light_characteristics.get(n).ActionType
        }
        else
        {actiomode=actionmode_}
        if (speed_==-1) {
        speed=(activity as MainActivity).light_characteristics.get(n).speed

        }
        else
        {speed=speed_}

        changeseekbarspeed(speed)

        (activity as MainActivity).setdata(
                    actiomode,
                    n,
                    redValue_m,
                    greenValue_m,
                    blueValue_m,
                    white_m,
                    Sint,
                    Vint,
                    colortodraw,
                    speed
            )
           if (rv.adapter!=null) {
               (rv.adapter as Groupview_modes).nowclicked = actiomode
           }
               rv.adapter?.notifyDataSetChanged();


    }

    public fun backsettingsfragment() {
        (activity as MainActivity).vpPager.setCurrentItem(0)
        val myFragment = (activity as MainActivity).supportFragmentManager.findFragmentByTag("f0")
        (myFragment as FirstFragment).rv.adapter?.notifyItemChanged((activity as MainActivity).nn.n)
        (activity as MainActivity).image_start.setVisibility(View.VISIBLE)
    }

}