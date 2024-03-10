package com.example.finanzas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class TagBalanceActivity : AppCompatActivity() {

    private lateinit var tagBalanceList : MutableList<TagBalance>
    private lateinit var lv_tag_balance : ListView
    private var startDateString : String = "null"
    private var endDateString : String = "null"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_balance)

        initAttributes()
        initComponents()
        initListeners()
        showList()
    }

    override fun onResume() {
        super.onResume()
        initComponents()
        initListeners()
        showList()
    }
    private fun initAttributes(){
        startDateString = intent.getStringExtra("startDateString").toString()
        endDateString = intent.getStringExtra("endDateString").toString()
    }

    private fun initComponents(){
        lv_tag_balance = findViewById(R.id.LV_tag_balance)

        val helper = DataBaseHelper(this)
        tagBalanceList = helper.get_tag_balance(startDateString, endDateString)

    }

    private fun showList(){
        lv_tag_balance.isClickable = true
        lv_tag_balance.adapter = TagBalanceAdapter(this, tagBalanceList)
        //show something if tagbalancelist is empty
    }

    private fun initListeners() {
        lv_tag_balance.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, ListEntryByTagActivity::class.java)
            intent.putExtra("tagId", tagBalanceList[position].get_id())
            intent.putExtra("startDateString", startDateString)
            intent.putExtra("endDateString", endDateString)
            startActivity(intent)
        }
    }
}