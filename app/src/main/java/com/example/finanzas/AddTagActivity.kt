package com.example.finanzas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AddTagActivity : AppCompatActivity() {

    private var isUpdate : Boolean = false
    private var tagId : Long = -1
    private lateinit var et_name : EditText
    private lateinit var s_isIncome : SwitchCompat
    private lateinit var tv_send : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_tag)

        initAttributes()
        initComponents()
        initListeners()
    }

    private fun initAttributes(){
        isUpdate = intent.getBooleanExtra("isUpdate", false)
        tagId = intent.getLongExtra("tagId", -1)
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

            if (isUpdate){
                helper.update_Tag(tag)
            }
            else{
                helper.add_Tag(tag)
            }
            finish()
        }
    }

    private fun getTag(): Tag {
        // ver de sanitizar el string
        val text = et_name.text.toString()
        val isIncome = s_isIncome.isChecked
        val tag = Tag(tagId, text, isIncome)
        return tag
    }
}