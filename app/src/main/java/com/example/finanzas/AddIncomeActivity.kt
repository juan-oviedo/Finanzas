package com.example.finanzas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar

class AddIncomeActivity : AppCompatActivity() {

    private var isIncome : Boolean = true
    private var isUpdate : Boolean = false
    private var entryID : Long = -1
    private var date : String = ""
    private var dateVisibility = false
    private lateinit var et_amount : EditText
    private lateinit var tv_tag: TextView
    private lateinit var tv_send : TextView
    private lateinit var tv_date : TextView
    private lateinit var dp_date : DatePicker
    private lateinit var showTagLauncher: ActivityResultLauncher<Intent>
    private var tagIds : LongArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_income)

        initAttributes()
        initComponents()
        initLayout()
        initListeners()
        initLauncher()
    }

    private fun initAttributes(){
        isIncome = intent.getBooleanExtra("isIncome", true)
        isUpdate = intent.getBooleanExtra("isUpdate", false)
        entryID = intent.getLongExtra("entryID", -1)
    }

    private fun initLayout(){
        if (!isIncome){
            val ll_body : LinearLayoutCompat = findViewById(R.id.LL_body)
            ll_body.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        /*
        val calendar = Calendar.getInstance()
        dp_date.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH),null)
         */
    }

    private fun initComponents(){
        et_amount = findViewById(R.id.ET_amount)
        tv_tag = findViewById(R.id.TV_tag)
        tv_send = findViewById(R.id.TV_send)
        tv_date = findViewById(R.id.TV_date)
        dp_date = findViewById(R.id.DP_date)
    }

    private fun initListeners(){
        tv_tag.setOnClickListener { navigateToShowTag() }
        tv_date.setOnClickListener {
            dateVisibility = ! dateVisibility
            if (dateVisibility){
                dp_date.visibility = VISIBLE
            }
            else{
                dp_date.visibility = GONE
            }
        }
        tv_send.setOnClickListener{
            val entry = getEntry()
            val helper = DataBaseHelper(this)

            if (isUpdate){
                helper.update_Entry(entry)
            }
            else {
                helper.add_Entry(entry)
            }
            finish()
        }
    }

    private fun navigateToShowTag (){
        val intent = Intent(this, ShowTagActivity::class.java)
        intent.putExtra("isIncome", isIncome)
        showTagLauncher.launch(intent)
    }

    private fun getEntry(): Entry {
        val text = et_amount.text.toString()
        var value = 0.0
        try {
            value = text.toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_LONG).show()
        }

        val time1 = LocalDateTime.now()
        val timeCreation = time1.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        val tagList = tagIds?.toList() ?: emptyList()

        val day = dp_date.dayOfMonth
        val month = dp_date.month + 1
        val year = dp_date.year

        val time2 = LocalDateTime.of(year, month, day, 0, 0)
        date = time2.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        Log.i("juan", "getEntry: $date")

        val entry = Entry(entryID, value, tagList, isIncome, timeCreation, date)

        return entry
    }

    private fun initLauncher(){
        showTagLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    tagIds = data.getLongArrayExtra("tagIds")
                }
            }
        }
    }
}