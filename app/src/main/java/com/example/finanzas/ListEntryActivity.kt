package com.example.finanzas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView

class ListEntryActivity : AppCompatActivity() {

    private lateinit var entryList : MutableList<Entry>
    private lateinit var lv_entry : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_entry)

        initComponents()
        showList()
    }

    private fun initComponents(){
        lv_entry = findViewById(R.id.LV_entry)

        val helper = DataBaseHelper(this)
        entryList = helper.get_all_Entry()
    }

    private fun showList(){
        lv_entry.isClickable = true
        lv_entry.adapter = EntryAdapter(this, entryList)
    }
}