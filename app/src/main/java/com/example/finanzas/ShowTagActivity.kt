package com.example.finanzas

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import android.widget.ListView
import android.widget.TextView

class ShowTagActivity : AppCompatActivity() {

    private var isIncome : Boolean = true
    private lateinit var tagList : MutableList<Tag>
    private lateinit var gv_tag : GridView
    private var idList : MutableList<Long> = mutableListOf()
    private lateinit var tv_send : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_tag)

        isIncome = intent.getBooleanExtra("isIncome", true)

        initComponents()
        showList()
        initListeners()
    }

    private fun initComponents(){
        gv_tag = findViewById(R.id.GV_tag)
        tv_send = findViewById(R.id.TV_send)

        val helper = DataBaseHelper(this)
        tagList = helper.get_all_tag_by_income(isIncome)
    }

    private fun showList(){
        gv_tag.isClickable = true
        gv_tag.adapter = TagAdapter(this, tagList, true, false)
    }

    private fun initListeners(){
        gv_tag.setOnItemClickListener { parent, view, position, id ->

            tagList[position].change_is_clicked()
            (parent.adapter as TagAdapter).notifyDataSetChanged()
            if (tagList[position].get_is_clicked()){
                idList.add(tagList[position].get_id())
            }
            else{
                idList.remove(tagList[position].get_id())
            }
        }

        tv_send.setOnClickListener {
            val resultIntent = Intent()
            resultIntent.putExtra("tagIds", idList.toLongArray())
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}