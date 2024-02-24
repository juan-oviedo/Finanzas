package com.example.finanzas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat

class AddIncomeActivity : AppCompatActivity() {

    private var isIncome : Boolean = true
    private lateinit var et_amount : EditText
    private lateinit var tv_tag: TextView
    private lateinit var tv_send : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isIncome = intent.getBooleanExtra("isIncome", true)
        setContentView(R.layout.activity_add_income)

        initLayout()
        initComponents()
        initListeners()
    }

    private fun initLayout(){
        if (!isIncome){
            val ll_body : LinearLayoutCompat = findViewById(R.id.LL_body)
            ll_body.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
    }

    private fun initComponents(){
        et_amount = findViewById(R.id.ET_amount)
        tv_tag = findViewById(R.id.TV_tag)
        tv_send = findViewById(R.id.TV_send)
    }

    private fun initListeners(){
        tv_tag.setOnClickListener { navigateToShowTag() }
        tv_send.setOnClickListener{
            val entry = getEntry()
            val helper = DataBaseHelper(this)
            helper.add_Entry(entry)
            finish()
        }
    }

    private fun navigateToShowTag (){
        val intent = Intent(this, ShowTagActivity::class.java)
        intent.putExtra("isIncome", isIncome)
        startActivity(intent)
    }

    private fun getEntry(): Entry {
        val text = et_amount.text.toString()
        var value = 0.0
        try {
            value = text.toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_LONG).show()
        }
        val entry = Entry(-1, value, 1, isIncome)

        return entry
    }
}