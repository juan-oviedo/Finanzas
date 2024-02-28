package com.example.finanzas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class TagBalanceActivity : AppCompatActivity() {

    private lateinit var tagBalanceList : MutableList<TagBalance>
    private lateinit var lv_tag_balance : ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tag_balance)

        initComponents()
        showList()
        //initListeners()
    }

    private fun initComponents(){
        lv_tag_balance = findViewById(R.id.LV_tag_balance)

        val helper = DataBaseHelper(this)
        tagBalanceList = helper.get_tag_balance()
    }

    private fun showList(){
        lv_tag_balance.isClickable = true
        lv_tag_balance.adapter = TagBalanceAdapter(this, tagBalanceList)
        //show something if tagbalanlist is empty
    }

    /*
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
    */
}