package com.example.finanzas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class BalanceActivity : AppCompatActivity() {

    private lateinit var tv_calculateBalance : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_balance)

        initComponents()
        initListeners()
    }

    private fun initComponents(){
        tv_calculateBalance = findViewById(R.id.TV_calculateBalance)
    }

    private fun initListeners(){
        tv_calculateBalance.setOnClickListener{navigateListEntry()}
    }

    private fun navigateListEntry (){
        val intent = Intent(this, ListEntryActivity::class.java)
        startActivity(intent)
    }
}