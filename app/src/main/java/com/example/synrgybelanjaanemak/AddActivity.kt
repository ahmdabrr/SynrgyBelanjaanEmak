package com.example.synrgybelanjaanemak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.recvi_item_belanjaan.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AddActivity : AppCompatActivity() {

    private lateinit var db: ItemDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        ItemDatabase.getInstance(this)?.let {
            db = it
        }

        btn_add.setOnClickListener{
            val itemBelanjaan = Item (
                id = null,
                name = et_add_namabelanjaan.text.toString(),
                quantity = et_add_quantity.text.toString().toInt(),
                satuan = et_add_satuan.text.toString(),
                hargaSatuan = et_add_harga.text.toString().toInt(),
                checkBarang = false
            )
            GlobalScope.launch {
                val totalAdded = db.itemDao().addItem(itemBelanjaan)
                runOnUiThread{
                    if (totalAdded > 0) {
                        Toast.makeText(this@AddActivity,"Data Sukses Disimpan", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@AddActivity,"Data Gagal Disimpan", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}