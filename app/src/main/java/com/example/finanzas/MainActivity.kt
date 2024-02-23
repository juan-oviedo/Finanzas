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
    private lateinit var add_cost: CardView
    private lateinit var balance: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initComponents()
        initListeners()
    }

    private fun initComponents(){
        add_income = findViewById(R.id.Add_income)
        add_tag = findViewById(R.id.Add_tag)
        add_cost = findViewById(R.id.Add_cost)
        balance = findViewById(R.id.Balance)
    }

    private fun initListeners(){
        add_income.setOnClickListener{ navigateToAddIncome() }
        add_tag.setOnClickListener{navigateToAddTag()}
        add_cost.setOnClickListener { navigateToAddCost() }
        balance.setOnClickListener { navigateToBalance() }
    }

    private fun navigateToAddIncome (){
        val intent = Intent(this, AddIncomeActivity::class.java)
        intent.putExtra("isIncome", true)
        startActivity(intent)
    }

    private fun navigateToAddTag (){
        val intent = Intent(this, AddTagActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAddCost (){
        val intent = Intent(this, AddIncomeActivity::class.java)
        intent.putExtra("isIncome", false)
        startActivity(intent)
    }

    private fun navigateToBalance(){
        val intent = Intent(this, BalanceActivity::class.java)
        startActivity(intent)
    }


}