package com.example.finanzas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.ListView

class ShowTagActivity : AppCompatActivity() {

    private var isIncome : Boolean = true
    private lateinit var tagList : MutableList<Tag>
    private lateinit var gv_tag : GridView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_tag)

        isIncome = intent.getBooleanExtra("isIncome", true)

        initComponents()
        showList()
    }

    private fun initComponents(){
        gv_tag = findViewById(R.id.GV_tag)
        val helper = DataBaseHelper(this)

        tagList = helper.get_all_tag(isIncome)
    }

    private fun showList(){
        gv_tag.isClickable = true
        gv_tag.adapter = TagAdapter(this, tagList)
    }
}