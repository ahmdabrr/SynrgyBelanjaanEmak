package com.example.synrgybelanjaanemak

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Item (
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "Nama belanjaan") var name: String,
    @ColumnInfo (name = "Quantity") var quantity: Int,
    @ColumnInfo (name = "Satuan") var satuan: String,
    @ColumnInfo (name = "Harga persatuan") var hargaSatuan: Int,
    @ColumnInfo (name = "Sudah dibeli") var checkBarang: Boolean
) : Parcelable