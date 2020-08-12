package com.example.synrgybelanjaanemak


import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recvi_item_belanjaan.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ItemAdapter(val listItem: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private lateinit var db: ItemDatabase

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun hitung(item: Item): Int {
            val hasil = item.hargaSatuan * item.quantity
            return hasil
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recvi_item_belanjaan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = Item(
            listItem[position].id,
            listItem[position].name,
            listItem[position].quantity,
            listItem[position].satuan,
            listItem[position].hargaSatuan,
            listItem[position].checkBarang
        )


        holder.itemView.et_cv_nama.setText(item.name)
        holder.itemView.et_cv_quantity.setText(item.quantity.toString())
        holder.itemView.et_cv_satuan.setText(item.satuan)
        holder.itemView.tv_cv_satuan.setText("/${item.satuan}")
        holder.itemView.et_cv_hargasatuan.setText(item.hargaSatuan.toString())


        ItemDatabase.getInstance(holder.itemView.context)?.let {
            db = it
        }

        holder.itemView.iv_delete.setOnClickListener {
            GlobalScope.launch {
                val totalRowDeleted = db.itemDao().deleteItem(listItem[position])
                (holder.itemView.context as MainActivity).runOnUiThread {
                    if (totalRowDeleted > 0) {
                        Toast.makeText(
                            holder.itemView.context,
                            "Data ${listItem[position].name} Sukses Dihapus",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            holder.itemView.context,
                            "Data ${listItem[position].name} Gagal Dihapus",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                (holder.itemView.context as MainActivity).fetchData()
            }
        }


        if (listItem[position].checkBarang) {
            holder.itemView.cv_recvi.setBackgroundColor(Color.parseColor("#FFFFEE58"))
            holder.itemView.cb_cv_sudah.isChecked = true
        } else {
            holder.itemView.cv_recvi.setBackgroundColor(Color.parseColor("#FFFFFF"))
            holder.itemView.cb_cv_sudah.isChecked = false
        }


        holder.itemView.tv_cv_totalharga.text = holder.hitung(item).toString()

        holder.itemView.cb_cv_sudah.setOnClickListener {
            if (holder.itemView.cb_cv_sudah.isChecked) {
                holder.itemView.cv_recvi.setBackgroundColor(Color.parseColor("#FFFFEE58"))
                item.apply {
                    checkBarang = true
                }
                GlobalScope.launch {
                    val rowUpdated = db.itemDao().updateItem(item)
                }
            } else {
                holder.itemView.cv_recvi.setBackgroundColor(Color.parseColor("#FFFFFF"))
                item.apply {
                    checkBarang = false
                }
                GlobalScope.launch {
                    val rowUpdated = db.itemDao().updateItem(item)
                }
            }
        }

        holder.itemView.et_cv_nama.addTextChangedListener {
            item.apply {
                name = holder.itemView.et_cv_nama.text.toString()
            }
            GlobalScope.launch {
                val rowUpdated = db.itemDao().updateItem(item)
            }
        }

        holder.itemView.et_cv_quantity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                GlobalScope.launch {
                    val rowUpdated = db.itemDao().updateItem(item)
                }
                val hitung = holder.hitung(item)
                holder.itemView.tv_cv_totalharga.text = hitung.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var textJumlah = holder.itemView.et_cv_quantity.text.toString()
                Log.d("Test", "$p0")
                if (textJumlah == "") {
                    textJumlah = "0"
                }

                item.apply {
                    quantity = textJumlah.toInt()
                }
            }
        })

        holder.itemView.et_cv_satuan.addTextChangedListener {
            item.apply {
                satuan = holder.itemView.et_cv_satuan.text.toString()
            }
            holder.itemView.tv_cv_satuan.setText("/${item.satuan}")
            GlobalScope.launch {
                val rowUpdated = db.itemDao().updateItem(item)
            }
        }

        holder.itemView.et_cv_hargasatuan.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    GlobalScope.launch {
                        val rowUpdated = db.itemDao().updateItem(item)

                        Log.d("Test1", "$p0")
                    }

                    val hitung = holder.hitung(item)

                    holder.itemView.tv_cv_totalharga.text = hitung.toString()
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    var hargaperSatuan = holder.itemView.et_cv_hargasatuan.text.toString()
                    Log.d("Test2", "$p0")
                    if (hargaperSatuan == "") {
                        hargaperSatuan = "0"
                    }

                    item.apply {
                        hargaSatuan = hargaperSatuan.toInt()
                    }
                }

            })
    }
}

