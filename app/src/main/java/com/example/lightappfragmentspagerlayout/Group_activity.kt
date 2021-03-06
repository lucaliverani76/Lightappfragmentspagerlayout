package com.example.lightappfragmentspagerlayout

import android.R.id.message
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText


class Groupview(
        var groups: Triple<MutableList<Groups_>, MutableList<String>, MutableList<Int>>
) : RecyclerView.Adapter<Groupview.ViewHolder>() {

    lateinit var context: Context
    override fun getItemViewType(position: Int): Int {

        val gg=groups.third.get(position)
        if (gg shr 16==1)
        {
            return 0
        }
        if (((gg shr 8) and 0xff)>0)
        {
            return 1
        }
        return 2
}

// Provide a direct reference to each of the views within a data item
// Used to cache the views within the item layout for fast access
inner class ViewHolder(listItemView: View) : RecyclerView.ViewHolder(listItemView) {

val textView_element = listItemView.findViewById(R.id.textView3) as TextView
val checkb = listItemView.findViewById(R.id.checkBox) as CheckBox
val image_edit = listItemView.findViewById(R.id.imageView_edit) as ImageView
val image_delete = listItemView.findViewById(R.id.imageView_delete) as ImageView


}

// ... constructor and member variables
// Usually involves inflating a layout from XML and returning the holder
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    context = parent.context
    val inflater = LayoutInflater.from(context)
// Inflate the custom layout
var contactView:View
if (viewType == 0) {
    contactView = inflater.inflate(R.layout.grouplayout2, parent, false)
}else{
    if (viewType == 1) {
        contactView = inflater.inflate(R.layout.grouplayout, parent, false)
    }
    else {
        contactView = inflater.inflate(R.layout.grouplayout3, parent, false)
    }
}

// Return a new holder instance

var viewHolder= ViewHolder(contactView)

return viewHolder
}

// Involves populating data into the item through holder
override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
// Get the data model based on position
//val contact: Contact = mContacts.get(position)
val nameofdevice=groups.second.get(position)
/*var marg_in=16


{marg_in=32}

var layoutParams = viewHolder.checkb.layoutParams as ConstraintLayout.LayoutParams
layoutParams.setMargins(marg_in, layoutParams.topMargin,layoutParams.rightMargin, layoutParams.bottomMargin)
viewHolder.checkb.layoutParams = layoutParams */

viewHolder.textView_element.text = nameofdevice //Type_[position]
//val mecolor: Int = 255 and 0xff shl 24 or (light_characteristics.get(position).red and 0xff shl 16) or (light_characteristics.get(position).green and 0xff shl 8) or (light_characteristics.get(position).blue and 0xff)
viewHolder.checkb.isChecked = false
viewHolder.image_edit.setImageResource(R.drawable.ic_baseline_mode_edit_24)
viewHolder.image_delete.setImageResource(R.drawable.ic_baseline_delete_24)

val activity = context as Activity

val gg=groups.third.get(position)


    if (((activity as Group_activity?)?.selectlights==true) && ((activity as Group_activity?)?.groupselected==(gg and 0xff))
            && (gg shr 16==1))
    {viewHolder.textView_element.setTextColor(Color.RED)}
    else
    {viewHolder.textView_element.setTextColor(Color.BLACK)}
if (gg shr 16==1)
{

}
else
{
    if ((activity as Group_activity?)?.selectlights==false)
    {viewHolder.checkb.setVisibility(View.INVISIBLE);}
    else
    {
     viewHolder.checkb.setVisibility(View.VISIBLE);
    if ((gg and 0xff)==(activity as Group_activity?)?.groupselected)
    {viewHolder.checkb.isChecked=true}
        else
    {viewHolder.checkb.isChecked=false}
    }

}
viewHolder.checkb.setOnClickListener()
{
    var gg2 = groups.first
    var gg=groups.third.get(position)
    val level=(gg and 0xff)-1
    val n=((gg shr 8) and 0xff)
    val activity = context as Activity
    val groupselected=(activity as Group_activity).groupselected-1
    if (groupselected!=level) {
        gg2.get(groupselected).listoflightsingroup.add(viewHolder.textView_element.text.toString())
    }
    else
    {
        var g=Groups_()
        g.nameofgroup=viewHolder.textView_element.text.toString()
        g.listoflightsingroup=mutableListOf<String>()
        g.listoflightsingroup.add(g.nameofgroup)
        g.ISGROUP=false
        gg2.add(g)
    }
    gg2.get(level).listoflightsingroup.removeAt(n)
    for (k in (gg2.size-1) downTo 0)
        if (gg2.get(k).listoflightsingroup.size==0)
        {
            gg2.removeAt((k))
            if (k < groupselected)
            {
                (activity as Group_activity).groupselected=(activity as Group_activity).groupselected-1
            }
        }
    var emoh= outputstrings(gg2)
    groups=Triple(gg2, emoh.first, emoh.second)
    (activity as Group_activity?)?.rv?.layoutManager = LinearLayoutManager(context)
}

viewHolder.image_edit.setOnClickListener(){

    val activity = context as Activity
    val gg=groups.third.get(position)
    if (((activity as Group_activity?)?.selectlights==true) && ((activity as Group_activity?)?.groupselected==(gg and 0xff)))
    {
        (activity as Group_activity?)?.selectlights=false
        (activity as Group_activity?)?.groupselected=-1

    }
    else
    {
     (activity as Group_activity?)?.selectlights=true
     (activity as Group_activity?)?.groupselected=(gg and 0xff)

    }

    (activity as Group_activity?)?.rv?.layoutManager = LinearLayoutManager(context)

}



viewHolder.image_delete.setOnClickListener(){


    var gg2 = groups.first
    var gg=groups.third.get(position)
    val level=(gg and 0xff)-1

    val activity = context as Activity



    for (n in (gg2.get(level).listoflightsingroup.size-1) downTo 1){

            var g = Groups_()
            g.nameofgroup = gg2.get(level).listoflightsingroup.get(n)
            g.listoflightsingroup = mutableListOf<String>()
            g.listoflightsingroup.add(g.nameofgroup)
            g.ISGROUP = false
            gg2.add(g)

            gg2.get(level).listoflightsingroup.removeAt(n)


          /*  for (k in (gg2.size - 1) downTo 0)
                if (gg2.get(k).listoflightsingroup.size == 0)
                    gg2.removeAt((k))*/
        }

    gg2.removeAt(level)

    var emoh = outputstrings(gg2)

    groups=Triple(gg2, emoh.first, emoh.second)

    (activity as Group_activity?)?.selectlights=false
    (activity as Group_activity?)?.groupselected=-1


    (activity as Group_activity?)?.rv?.layoutManager = LinearLayoutManager(context)

}


}


// Returns the total count of items in the list
override fun getItemCount(): Int {
    return groups.second.size
}
}



class Group_activity : AppCompatActivity() {


lateinit var image_music2: ImageView
lateinit var image_mic2: ImageView
lateinit var image_group2: ImageView
lateinit var imageback:ImageView


lateinit var rv:RecyclerView
lateinit var listofdevices_string: Array<String>
var selectlights:Boolean=false
var groupselected:Int=-1
lateinit var groupstructure:Pair<MutableList<String>, MutableList<Int>>

lateinit var listofgroups:MutableList<Groups_>

lateinit var buttonadd:Button
lateinit var tinput:TextInputEditText





override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setContentView(R.layout.activity_group_activity)
val b = this.intent.extras
listofdevices_string = b!!.getStringArray("EXTRA_SESSION_ID") as Array<String>


    listofdevices_string= arrayOf("_%Master%_GruppoPippo", "_%Slave%_Light1", "_%Slave%_Light2",
            "_%Master%_GruppoPluto", "_%Slave%_Light3", "_%Slave%_Light4", "Light5")

listofgroups= outputgroups(listofdevices_string)
groupstructure= outputstrings(listofgroups)

image_music2 = findViewById<View>(R.id.image_music2) as ImageView
image_mic2 = findViewById<View>(R.id.image_mic2) as ImageView
image_group2 = findViewById<View>(R.id.image_group2) as ImageView
image_music2.setImageResource(R.drawable.ic_audiotrack_24px)
image_mic2.setImageResource(R.drawable.ic_mic_24px)
image_group2.setImageResource(R.drawable.ic_baseline_subject_24)
    tinput = findViewById<View>(R.id.textInputEditText) as TextInputEditText
buttonadd = findViewById<View>(R.id.button3) as Button
    imageback= findViewById<View>(R.id.imageView_back2) as ImageView


    imageback.setOnClickListener(){
        ResultsandFinish()
        finish();
    }
    buttonadd.setOnClickListener(){


        var Groupname=tinput.text.toString()
        for (j in 0..listofgroups.size-1){
            if (listofgroups.get(j).nameofgroup ==tinput.text.toString()){
                Groupname = tinput.text.toString() + "_"
                break
            }

        }
        if (Groupname!=""){
            var g = Groups_()
            g.ISGROUP=true
            g.nameofgroup = Groupname
            g.listoflightsingroup = mutableListOf<String>()
            g.listoflightsingroup.add(Groupname)
            listofgroups.add(g)
            groupstructure = outputstrings(listofgroups)

            val adapter = Groupview(Triple(listofgroups, groupstructure.first, groupstructure.second))
            rv.adapter = adapter
            rv.layoutManager = LinearLayoutManager(this)

            tinput.setText("")
        }

        val inputManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(getCurrentFocus()?.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

    }



rv = findViewById<View>(R.id.RecView_groups) as RecyclerView

val adapter = Groupview(Triple(listofgroups, groupstructure.first, groupstructure.second))


// Attach the adapter to the recyclerview to populate items
rv.adapter = adapter
// Set layout manager to position the items
rv.layoutManager = LinearLayoutManager(this)
}


    fun ResultsandFinish(){
        val intent = Intent()
        intent.putExtra("MESSAGE", makestrings(listofgroups))
        setResult(RESULT_OK, intent)
        finish();
    }


    override fun onBackPressed() {
        ResultsandFinish()
        return;
    }
}