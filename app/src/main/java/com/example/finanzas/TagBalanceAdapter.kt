package com.example.finanzas

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat

class TagBalanceAdapter (private val context : Activity, private val tagBalanceList : MutableList<TagBalance>)
                        : ArrayAdapter<TagBalance>(context, R.layout.show_tag_balance, tagBalanceList){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater : LayoutInflater = LayoutInflater.from(context)
        val view : View = inflater.inflate(R.layout.show_tag_balance,null)

        val tv_tag : TextView = view.findViewById(R.id.TV_tag)
        val tv_amount : TextView = view.findViewById(R.id.TV_amount)

        tv_tag.text = tagBalanceList[position].get_name()
        tv_amount.text = tagBalanceList[position].get_amount().toString()

        if( ! tagBalanceList[position].get_is_income()){
            tv_tag.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
            tv_amount.setBackgroundColor(ContextCompat.getColor(context, R.color.red))
        }

        return view
    }
}