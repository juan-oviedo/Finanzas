package com.example.finanzas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddTagActivity : AppCompatActivity() {

    private lateinit var et_name : EditText
    private lateinit var s_isIncome : SwitchCompat
    private lateinit var tv_send : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tag)

        initComponents()
        initListeners()
    }

    private fun initComponents(){
        et_name = findViewById(R.id.ET_name)
        s_isIncome = findViewById(R.id.S_isIncome)
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
        val isIncome = s_isIncome.isChecked

        val timestampString = LocalDateTime.now()
        val timeCreation = timestampString.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        val tag = Tag(-1, text, isIncome, timeCreation)
        return tag
    }
}