package com.example.finanzas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class AddIncomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_income)

        val dataBaseHelper = DataBaseHelper(this)
        val id = dataBaseHelper.add()

        Log.i("juan", "$id")
    }
}