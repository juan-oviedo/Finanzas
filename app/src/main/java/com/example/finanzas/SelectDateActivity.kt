package com.example.finanzas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import androidx.cardview.widget.CardView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SelectDateActivity : AppCompatActivity() {

    private var isEntry = true
    private var custom = false
    private var start = false
    private var end = false
    private var custom_to_send = false
    private var startDateString : String? = null
    private var endDateString : String? = null
    private lateinit var tv_monthly: TextView
    private lateinit var tv_historical: TextView
    private lateinit var tv_customised: TextView
    private lateinit var tv_start: TextView
    private lateinit var tv_end: TextView
    private lateinit var tv_send: TextView
    private lateinit var dp_start: DatePicker
    private lateinit var dp_end: DatePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_date)


        initComponents()
        initAttributes()
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        defoultAttributes()
        initComponents()
        initListeners()
    }

    private fun defoultAttributes(){
        custom = false
        start = false
        end = false
        custom_to_send = false
        startDateString = null
        endDateString = null
    }

    private fun initComponents(){
        tv_monthly = findViewById(R.id.TV_monthly)
        tv_historical = findViewById(R.id.TV_historical)
        tv_customised = findViewById(R.id.TV_customised)
        tv_start = findViewById(R.id.TV_start)
        tv_end = findViewById(R.id.TV_end)
        tv_send = findViewById(R.id.TV_send)
        dp_start = findViewById(R.id.DP_start)
        dp_end = findViewById(R.id.DP_end)
    }

    private fun initAttributes(){
        isEntry = intent.getBooleanExtra("isEntry", true)
    }

    private fun initListeners(){
        tv_monthly.setOnClickListener {
            getMonthly()
            navigateToTheCorrectActivity()
        }
        tv_historical.setOnClickListener { navigateToTheCorrectActivity() }
        tv_send.setOnClickListener {
            custom_to_send = true
            getDate()
            navigateToTheCorrectActivity()
        }
        tv_customised.setOnClickListener {
            custom = ! custom
            if (custom){
                tv_start.visibility = View.VISIBLE
                tv_end.visibility = View.VISIBLE
                tv_send.visibility = View.VISIBLE
            }
            else{
                tv_start.visibility = View.GONE
                tv_end.visibility = View.GONE
                tv_send.visibility = View.GONE
            }
        }
        tv_start.setOnClickListener {
            start = !start
            if (start){
                dp_start.visibility = View.VISIBLE
            }
            else{
                dp_start.visibility = View.GONE
            }
        }
        tv_end.setOnClickListener {
            end = !end
            if (end){
                dp_end.visibility = View.VISIBLE
            }
            else{
                dp_end.visibility = View.GONE
            }
        }
    }

    private fun getMonthly(){
        var time = LocalDateTime.now()
        val year = time.year
        val month = time.month
        time = LocalDateTime.of(year, month, 1, 0, 0)
        startDateString = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        time = LocalDateTime.of(year, month + 1, 1, 0, 0)
        time = time.minusDays(1)
        endDateString = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    private fun getDate(){
        var day = dp_start.dayOfMonth
        var month = dp_start.month + 1
        var year = dp_start.year

        val timeStart = LocalDateTime.of(year, month, day, 0, 0)

        day = dp_end.dayOfMonth
        month = dp_end.month + 1
        year = dp_end.year

        val timeEnd = LocalDateTime.of(year, month, day, 0, 0)

        if(timeStart <= timeEnd){
            //case when timeStart is before timeEnd
            startDateString = timeStart.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            endDateString = timeEnd.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }
        else{
            //case when timeStart is after timeEnd
            startDateString = timeEnd.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            endDateString = timeStart.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }
    }

    private fun navigateToTheCorrectActivity(){
        intent = if (isEntry){
            //case is an entry balance
            Intent(this, ListEntryActivity::class.java)
        } else{
            //case is a tag balance
            Intent(this, TagBalanceActivity::class.java)
        }
        intent.putExtra("startDateString", startDateString)
        intent.putExtra("endDateString", endDateString)
        startActivity(intent)
    }
}