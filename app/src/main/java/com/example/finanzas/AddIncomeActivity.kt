package com.example.finanzas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat

class AddIncomeActivity : AppCompatActivity() {

    private var isIncome : Boolean = true
    private lateinit var et_amount : EditText
    private lateinit var tv_tag: TextView
    private lateinit var tv_send : TextView
    private lateinit var showTagLauncher: ActivityResultLauncher<Intent>
    private var tagIds : LongArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isIncome = intent.getBooleanExtra("isIncome", true)
        setContentView(R.layout.activity_add_income)

        initLayout()
        initComponents()
        initListeners()
        initLauncher()
    }

    private fun initLayout(){
        if (!isIncome){
            val ll_body : LinearLayoutCompat = findViewById(R.id.LL_body)
            ll_body.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
    }

    private fun initComponents(){
        et_amount = findViewById(R.id.ET_amount)
        tv_tag = findViewById(R.id.TV_tag)
        tv_send = findViewById(R.id.TV_send)
    }

    private fun initListeners(){
        tv_tag.setOnClickListener { navigateToShowTag() }
        tv_send.setOnClickListener{
            val entry = getEntry()
            val helper = DataBaseHelper(this)
            helper.add_Entry(entry)
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

        var tagId :Long?  = 1
        if (tagIds?.isNotEmpty() == true){
            tagId = tagIds?.get(0)
        }
        val entry = Entry(-1, value, tagId, isIncome)

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