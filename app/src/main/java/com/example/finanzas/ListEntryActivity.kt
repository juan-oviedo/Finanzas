package com.example.finanzas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView

class ListEntryActivity : AppCompatActivity() {

    private lateinit var entryList : MutableList<Entry>
    private lateinit var lv_entry : ListView
    private lateinit var tv_cost : TextView
    private lateinit var tv_income : TextView
    private lateinit var tv_balance : TextView
    private var startDateString : String = "null"
    private var endDateString : String = "null"

    //through this variable i am going to get the results in the function get All Entry
    private var result = Results()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_entry)

        initAttributes()
        initComponents()
        initListeners()
        showList()
    }

    override fun onResume() {
        super.onResume()
        initComponents()
        initListeners()
        showList()
    }

    private fun initAttributes(){
        startDateString = intent.getStringExtra("startDateString").toString()
        endDateString = intent.getStringExtra("endDateString").toString()
    }

    private fun initComponents(){
        lv_entry = findViewById(R.id.LV_entry)
        tv_cost = findViewById(R.id.TV_cost)
        tv_income = findViewById(R.id.TV_income)
        tv_balance = findViewById(R.id.TV_balance)

        val helper = DataBaseHelper(this)
        if (startDateString == "null" && endDateString == "null"){
            entryList = helper.get_all_Entry(result)
        }
        else{
            entryList = helper.get_entry_by_date(startDateString, endDateString, result)
        }
    }

    private fun showList(){
        lv_entry.isClickable = true
        lv_entry.adapter = EntryAdapter(this, entryList)
        tv_cost.text = result.getCost().toString()
        tv_income.text = result.getIncome().toString()
        tv_balance.text = result.getResult().toString()
        TODO("agregar al adapter y al layout que muestre la fecha de cada entrada")
    }

    private fun initListeners(){
        lv_entry.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, DetailEntryActivity::class.java)
            intent.putExtra("id", entryList[position].get_id())
            startActivity(intent)
        }
    }
}