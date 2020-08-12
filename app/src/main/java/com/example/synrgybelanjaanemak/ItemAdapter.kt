package com.example.synrgybelanjaanemak


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recvi_item_belanjaan.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ItemAdapter(val listItem: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    private lateinit var db: ItemDatabase
    private lateinit var item: Item

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recvi_item_belanjaan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.et_cv_nama.setText(listItem[position].name)
        holder.itemView.et_cv_quantity.setText(listItem[position].quantity.toString())
        holder.itemView.et_cv_satuan.setText(listItem[position].satuan.toString())
        holder.itemView.et_cv_hargasatuan.setText(listItem[position].hargaSatuan.toString())

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

        if (holder.itemView.cb_cv_sudah.isChecked == true){
            holder.itemView.cv_recvi.setBackgroundColor(Color.parseColor("#FFFFEE58"))
        }

        holder.itemView.et_cv_nama.addTextChangedListener {
            GlobalScope.launch {
                item.apply {
                    name = holder.itemView.et_cv_nama.text.toString()
                }
                GlobalScope.launch {
                    val rowUpdated = db.itemDao().updateItem(item)
                    (holder.itemView.context as MainActivity).runOnUiThread {
                        if (rowUpdated > 0) {
                            Toast.makeText(
                                holder.itemView.context,
                                "Data ${listItem[position].name} Sukses terupdate",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(
                                holder.itemView.context,
                                "Data ${listItem[position].name} Gagal terupdate",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
        //nambahain addActivity (disini)

    }
}