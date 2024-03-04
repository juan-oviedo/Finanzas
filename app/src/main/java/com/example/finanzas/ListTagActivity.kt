package com.example.finanzas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.TextView

class ListTagActivity : AppCompatActivity() {

    private lateinit var tagList : MutableList<Tag>
    private lateinit var gv_tag : GridView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_tag)

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
    private fun initComponents(){
        gv_tag = findViewById(R.id.GV_tag)

        val helper = DataBaseHelper(this)
        tagList = helper.get_all_tags()
    }

    private fun showList(){
        gv_tag.isClickable = true
        gv_tag.adapter = TagAdapter(this, tagList, false, true)
    }

    private fun initListeners(){
        gv_tag.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DetailTagActivity::class.java)
            intent.putExtra("id", tagList[position].get_id())
            startActivity(intent)
        }
    }
}