package com.example.finanzas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.GridView
import android.widget.TextView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class DetailEntryActivity : AppCompatActivity() {

    private var entryId :Long = 0
    private lateinit var entry : Entry
    private lateinit var ll_body : ConstraintLayout
    private lateinit var tv_amount : TextView
    private lateinit var gv_tag : GridView
    private lateinit var tv_edit : TextView
    private lateinit var tv_delete : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_entry)


        initAttributes()
        initComponents()
        initLayout()
        initListeners()
        showList()
    }

    override fun onResume() {
        super.onResume()
        initComponents()
        initLayout()
        initListeners()
    }

    private fun initAttributes(){
        entryId = intent.getLongExtra("id", 0)
    }

    private fun initComponents(){
        ll_body = findViewById(R.id.CL_root)
        tv_amount = findViewById(R.id.TV_amount)
        gv_tag = findViewById(R.id.GV_tag)
        tv_edit = findViewById(R.id.TV_edit)
        tv_delete = findViewById(R.id.TV_delete)

        val helper = DataBaseHelper(this)
        entry = helper.get_entry(entryId)
    }

    private fun initLayout(){

        tv_amount.text = entry.get_amount().toString()

        if (! entry.get_income()){
            val ll_body : ConstraintLayout = findViewById(R.id.CL_root)
            ll_body.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
        TODO("agregar que se muestre la fecha de la entrada")
    }

    private fun initListeners(){
        tv_edit.setOnClickListener { navigateToAddIncome() }

        tv_delete.setOnClickListener {
            val helper = DataBaseHelper(this)
            helper.delete_Entry(entry)
            finish()
        }
    }

    private fun navigateToAddIncome(){
        val intent = Intent(this, AddIncomeActivity::class.java)
        intent.putExtra("isIncome", entry.get_income())
        intent.putExtra("isUpdate", true)
        intent.putExtra("entryID", entryId)
        startActivity(intent)
    }

    private fun showList(){
        val helper = DataBaseHelper(this)
        val tagList : MutableList<Tag> = mutableListOf()
        for (tagId in entry.get_tags()){
            val tag = helper.get_Tag(tagId)
            if (tag.get_id() != -1L){
                tagList.add(tag)
            }
        }
        gv_tag.adapter = TagAdapter(this, tagList, false, false)

    }
}