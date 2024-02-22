package com.example.finanzas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class AddIncomeActivity : AppCompatActivity() {

    private lateinit var et_amount : EditText
    private lateinit var tv_send : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income)

        initComponents()
        initListeners()
    }

    private fun initComponents(){
        et_amount = findViewById(R.id.ET_amount)
        tv_send = findViewById(R.id.TV_send)
    }

    private fun initListeners(){
        tv_send.setOnClickListener{
            val entry = getEntry()
            val helper = DataBaseHelper(this)
            helper.add_Entry(entry)
            finish()
        }
    }

    private fun getEntry(): Entry {
        val text = et_amount.text.toString()

        var value = 0.0
        try {
            value = text.toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_LONG).show()
        }
        val entry = Entry(-1, value, 1)

        return entry
    }
}