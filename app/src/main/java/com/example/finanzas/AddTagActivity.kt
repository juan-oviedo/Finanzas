package com.example.finanzas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class AddTagActivity : AppCompatActivity() {

    private lateinit var et_name : EditText
    private lateinit var tv_send : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tag)

        initComponents()
        initListeners()
    }

    private fun initComponents(){
        et_name = findViewById(R.id.ET_name)
        tv_send = findViewById(R.id.TV_send)
    }

    private fun initListeners(){
        tv_send.setOnClickListener{
            val tag = getTag()
            val helper = DataBaseHelper(this)
            helper.add_Tag(tag)
            finish()
        }
    }

    private fun getTag(): Tag {
        // ver de sanitizar el string
        val text = et_name.text.toString()
        val tag = Tag(-1, text)
        return tag
    }
}