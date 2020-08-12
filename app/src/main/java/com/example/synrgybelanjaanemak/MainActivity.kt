package com.example.synrgybelanjaanemak

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var db : ItemDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ItemDatabase.getInstance(this)?.let {
            db = it
        }

        fab.setOnClickListener{
            val intentGoToActivityAdd = Intent(this, AddActivity::class.java)
            startActivity(intentGoToActivityAdd)
        }
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData(){
        GlobalScope.launch {
            val listItem = db.itemDao().readAllItem()

            runOnUiThread {
                val adapter = ItemAdapter(listItem)
                rv_layout.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                rv_layout.adapter = adapter
            }
        }
    }
}