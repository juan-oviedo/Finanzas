package com.example.finanzas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class TagsActivity : AppCompatActivity() {

    private lateinit var tv_listTag : TextView
    private lateinit var tv_addATag : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tags)

        initComponents()
        initListeners()
    }

    private fun initComponents(){
        tv_listTag = findViewById(R.id.TV_listTag)
        tv_addATag = findViewById(R.id.TV_addATag)
    }

    private fun initListeners(){
        tv_listTag.setOnClickListener{navigateListTags()}
        tv_addATag.setOnClickListener { navigateToAddTag() }
    }

    private fun navigateListTags (){
        val intent = Intent(this, ListTagActivity::class.java)
        startActivity(intent)
    }

    private  fun navigateToAddTag(){
        val intent = Intent(this, AddTagActivity::class.java)
        startActivity(intent)
    }
}