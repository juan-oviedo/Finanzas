package com.example.finanzas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat

class DetailTagActivity : AppCompatActivity() {

    private var tagId :Long = 0
    private lateinit var tag : Tag
    private lateinit var ll_body : ConstraintLayout
    private lateinit var tv_name : TextView
    private lateinit var tv_edit : TextView
    private lateinit var tv_delete : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tag)

        initAttributes()
        initComponents()
        initLayout()
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        initComponents()
        initLayout()
        initListeners()
    }

    private fun initAttributes(){
        tagId = intent.getLongExtra("id", 0)
    }

    private fun initComponents(){
        ll_body = findViewById(R.id.CL_root)
        tv_name = findViewById(R.id.TV_name)
        tv_edit = findViewById(R.id.TV_edit)
        tv_delete = findViewById(R.id.TV_delete)

        val helper = DataBaseHelper(this)
        tag = helper.get_Tag(tagId)
    }

    private fun initLayout(){

        tv_name.text = tag.get_name()

        if (! tag.get_is_income()){
            val ll_body : ConstraintLayout = findViewById(R.id.CL_root)
            ll_body.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
    }

    private fun initListeners(){
        tv_edit.setOnClickListener { navigateToAddTag() }

        tv_delete.setOnClickListener {
            val helper = DataBaseHelper(this)
            helper.delete_Tag(tag)
            finish()
        }
    }

    private fun navigateToAddTag(){
        val intent = Intent(this, AddTagActivity::class.java)
        intent.putExtra("isUpdate", true)
        intent.putExtra("tagId", tagId)
        startActivity(intent)
    }
}
