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
        tv_calculateBalance.setOnClickListener{navigateListEntry()}
        tv_balanceForTag.setOnClickListener { navigateToTagBalance() }
    }

    private fun navigateListEntry (){
        val intent = Intent(this, ListEntryActivity::class.java)
        startActivity(intent)
        TODO("agregar popup intermedio para elejir fecha")
        /*
        una posible solucion es que calcule siempre el mensual
        y dentro de listEntry haya un boton para seleccionar el intervalo
         */
    }

    private  fun navigateToTagBalance(){
        val intent = Intent(this, TagBalanceActivity::class.java)
        startActivity(intent)
        TODO("agregar popup intermedio para elejir fecha")
    }
}