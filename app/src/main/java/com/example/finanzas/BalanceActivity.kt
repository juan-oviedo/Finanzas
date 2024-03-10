package com.example.finanzas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class BalanceActivity : AppCompatActivity() {

    private lateinit var tv_calculateBalance : TextView
    private lateinit var tv_balanceForTag : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)

        initComponents()
        initListeners()
    }

    private fun initComponents(){
        tv_calculateBalance = findViewById(R.id.TV_calculateBalance)
        tv_balanceForTag = findViewById(R.id.TV_BalanceForTag)
    }

    private fun initListeners(){
        tv_calculateBalance.setOnClickListener{ navigateToSelectDate(true)}
        tv_balanceForTag.setOnClickListener { navigateToSelectDate (false) }
    }

    private  fun navigateToSelectDate( isEntry : Boolean){
        val intent = Intent(this, SelectDateActivity::class.java)
        intent.putExtra("isEntry", isEntry)
        startActivity(intent)
    }
}