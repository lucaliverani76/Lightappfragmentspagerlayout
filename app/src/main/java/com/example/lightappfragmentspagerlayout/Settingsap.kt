package com.example.lightappfragmentspagerlayout

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Groupview_modes(
    var modes: Array<String>
) : RecyclerView.Adapter<Groupview_modes.ViewHolder>() {

    lateinit var context: Context


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
// Get the data model based on position
//val contact: Contact = mContacts.get(position)



        viewHolder.textView_element.text = modes.get(position) //Type_[position]

        viewHolder.checkb.isChecked = false
        viewHolder.image_edit.setImageResource(R.drawable.ic_baseline_mode_edit_24)





        viewHolder.checkb.setOnClickListener()
        {

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

        rv = layoutInflater_view.findViewById<View>(R.id.recview_modes) as RecyclerView

        val adapter = Groupview_modes(getResources().getStringArray(R.array.programs_array))


// Attach the adapter to the recyclerview to populate items
        rv.adapter = adapter
// Set layout manager to position the items
        rv.layoutManager = LinearLayoutManager(thiscontext)


    }

        companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Settingsap.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Settingsap().apply {
                arguments = Bundle().apply {

                }
            }
    }
}