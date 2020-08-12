package com.example.synrgybelanjaanemak

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface ItemDao {
    @Insert(onConflict = REPLACE)
    //Return long -> Jumlah data yang berhasil disimpan
    fun addItem(item: Item): Long

    @Query("SELECT * FROM Item")
    // List<Item> --> Hasil dari Query
    fun readAllItem(): List<Item>

    @Update
    // Int --> jumlah data/baris yang berhasil diupdate
    fun updateItem(item: Item): Int

    @Delete
    // Int --> Jumlah data yang berhasil dihapus
    fun deleteItem(item: Item): Int
}