package com.example.finanzas

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class TagAdapter (private val context : Activity, private val tagList : MutableList<Tag>)
                : ArrayAdapter<Tag>(context, R.layout.show_tag, tagList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.show_tag,null)

        val tag : TextView = view.findViewById(R.id.TV_tag)

        tag.text = tagList[position].get_name()

        if (tagList[position].get_is_clicked()){
            tag.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_700))
        }
        else{
            tag.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200))
        }

        return view
    }
}