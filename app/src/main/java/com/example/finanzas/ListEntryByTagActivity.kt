package com.example.finanzas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView

class ListEntryByTagActivity : AppCompatActivity() {

    private lateinit var entryList: MutableList<Entry>
    private lateinit var tag: Tag
    private lateinit var tv_tag: TextView
    private lateinit var lv_entry: ListView
    private lateinit var tv_balance: TextView
    private var tagId : Long = -1
    private var startDateString: String = "null"
    private var endDateString: String = "null"

    //through this variable i am going to get the results in the function get All Entry
    private var result = Results()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_entry_by_tag)
        initAttributes()
        initComponents()
        initListeners()
        showList()
    }

    override fun onResume() {
        super.onResume()
        defaultAttributes()
        initComponents()
        initListeners()
        showList()
    }

    private fun defaultAttributes() {
        result = Results()
    }

    private fun initAttributes() {
        tagId = intent.getLongExtra("tagId", -1)
        startDateString = intent.getStringExtra("startDateString").toString()
        endDateString = intent.getStringExtra("endDateString").toString()
    }

    private fun initComponents() {
        tv_tag = findViewById(R.id.TV_tag)
        lv_entry = findViewById(R.id.LV_entry)
        tv_balance = findViewById(R.id.TV_balance)

        val helper = DataBaseHelper(this)

        entryList = helper.get_entrys_by_tag(startDateString, endDateString, tagId, result)
        tag = helper.get_Tag(tagId)
    }

    private fun showList() {
        tv_tag.text = tag.get_name()
        lv_entry.isClickable = true
        lv_entry.adapter = EntryAdapter(this, entryList)
        tv_balance.text = result.getResult().toString()
    }

    private fun initListeners() {
        lv_entry.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, DetailEntryActivity::class.java)
            intent.putExtra("id", entryList[position].get_id())
            startActivity(intent)
        }
    }
}