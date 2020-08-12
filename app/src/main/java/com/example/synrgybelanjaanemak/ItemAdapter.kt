package com.example.synrgybelanjaanemak


import android.graphics.Color
import android.util.Log
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
        holder.itemView.et_cv_satuan.setText(listItem[position].satuan)
        holder.itemView.tv_cv_satuan.setText(listItem[position].satuan)
        holder.itemView.et_cv_hargasatuan.setText(listItem[position].hargaSatuan.toString())
        var totalHarga = listItem[position].quantity * listItem[position].hargaSatuan
        Log.d("Binar","$totalHarga")
        holder.itemView.tv_cv_totalharga.setText("$totalHarga")

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

        var item = Item(
            listItem[position].id,
            listItem[position].name,
            listItem[position].quantity,
            listItem[position].satuan,
            listItem[position].hargaSatuan,
            listItem[position].checkBarang
        )

        holder.itemView.cb_cv_sudah.setOnClickListener {
            item.apply {
                checkBarang = holder.itemView.cb_cv_sudah.isChecked
            }
            GlobalScope.launch {
                val rowUpdated = db.itemDao().updateItem(item)
            }
            if (holder.itemView.cb_cv_sudah.isChecked) {
                holder.itemView.cv_recvi.setBackgroundColor(Color.parseColor("#FFFFEE58"))
                Toast.makeText(
                    holder.itemView.context,
                    "Sudah dibeli",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                holder.itemView.cv_recvi.setBackgroundColor(Color.parseColor("#FFFFFF"))
                Toast.makeText(
                    holder.itemView.context,
                    "Belum terbeli",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        holder.itemView.et_cv_nama.addTextChangedListener {
            item.apply {
                name = holder.itemView.et_cv_nama.text.toString()
            }
            GlobalScope.launch {
                val rowUpdated = db.itemDao().updateItem(item)
                (holder.itemView.context as MainActivity).runOnUiThread {
                    if (rowUpdated > 0) {
                        Toast.makeText(
                            holder.itemView.context,
                            "Nama terupdate",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            holder.itemView.context,
                            "Nama gagal terupdate",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            holder.itemView.et_cv_quantity.addTextChangedListener {
                item.apply {
                    quantity = holder.itemView.et_cv_quantity.text.toString().toInt()
                }
                GlobalScope.launch {
                    val rowUpdated = db.itemDao().updateItem(item)
                    (holder.itemView.context as MainActivity).runOnUiThread {
                        if (rowUpdated > 0) {
                            Toast.makeText(
                                holder.itemView.context,
                                "Quantity terupdate",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                holder.itemView.context,
                                "Quantity gagal terupdate",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            holder.itemView.et_cv_satuan.addTextChangedListener {
                item.apply {
                    satuan = holder.itemView.et_cv_satuan.text.toString()
                }
                GlobalScope.launch {
                    val rowUpdated = db.itemDao().updateItem(item)
                    (holder.itemView.context as MainActivity).runOnUiThread {
                        if (rowUpdated > 0) {
                            Toast.makeText(
                                holder.itemView.context,
                                "Satuan terupdate",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                holder.itemView.context,
                                "Satuan gagal terupdate",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            holder.itemView.et_cv_hargasatuan.addTextChangedListener {
                item.apply {
                    hargaSatuan = holder.itemView.et_cv_hargasatuan.text.toString().toInt()
                }
                GlobalScope.launch {
                    val rowUpdated = db.itemDao().updateItem(item)
                    (holder.itemView.context as MainActivity).runOnUiThread {
                        if (rowUpdated > 0) {
                            Toast.makeText(
                                holder.itemView.context,
                                "Harga satuan terupdate",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                holder.itemView.context,
                                "Harga satuan gagal terupdate",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }


        }
    }
}