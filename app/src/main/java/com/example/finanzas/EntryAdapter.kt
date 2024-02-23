package com.example.finanzas

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class EntryAdapter (private val context : Activity, private val entryList : MutableList<Entry>)
                    : ArrayAdapter<Entry>(context, R.layout.list_entry, entryList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.list_entry,null)

        val amount : TextView = view.findViewById(R.id.TV_amount)

        amount.text = entryList[position].get_amount().toString()
        if (!entryList[position].get_income()){
            amount.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
        }

        return view
    }
}