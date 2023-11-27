package com.example.newapp.ui.login.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
public interface UserDao {
    @Insert
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item )

    @Delete
    suspend fun delete(item: Item)

    @Query("DELETE FROM item_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM item_table WHERE id = :key")
    suspend fun get(key: Int): Item?

    @Query("SELECT * FROM item_table WHERE className = :key")
    suspend fun getByClassName(key: String): Item?

    @Query("UPDATE item_table SET className = :className, classDetails = :details, date= :date WHERE id = :id")
    suspend fun updateItemById(id: Int, className: String, details: String, date: String)


    @Query("SELECT * FROM item_table ORDER BY className ASC")
    suspend fun getAll(): List<Item>
}
