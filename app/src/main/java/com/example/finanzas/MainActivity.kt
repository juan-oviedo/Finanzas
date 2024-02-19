package com.example.finanzas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.cardview.widget.CardView
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var add_income: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        initListeners()
    }

    private fun initComponents(){
        add_income = findViewById(R.id.Add_income)
    }

    private fun initListeners(){
        add_income.setOnClickListener{ navigateToAddIncome() }
    }

    private fun navigateToAddIncome (){
        val intent = Intent(this, AddIncomeActivity::class.java)
        startActivity(intent)
    }


}