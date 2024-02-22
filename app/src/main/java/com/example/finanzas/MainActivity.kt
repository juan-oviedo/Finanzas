package com.example.finanzas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.cardview.widget.CardView
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var add_income: CardView
    private lateinit var add_tag: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        initListeners()
    }

    private fun initComponents(){
        add_income = findViewById(R.id.Add_income)
        add_tag = findViewById(R.id.Add_tag)
    }

    private fun initListeners(){
        add_income.setOnClickListener{ navigateToAddIncome() }
        add_tag.setOnClickListener{navigateToAddTag()}
    }

    private fun navigateToAddIncome (){
        val intent = Intent(this, AddIncomeActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAddTag (){
        val intent = Intent(this, AddTagActivity::class.java)
        startActivity(intent)
    }


}