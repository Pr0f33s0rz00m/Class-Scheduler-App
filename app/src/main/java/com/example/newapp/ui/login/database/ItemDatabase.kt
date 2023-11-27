package com.example.newapp.ui.login.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 4, exportSchema = false)
public abstract class ItemDatabase: RoomDatabase() {
    abstract val itemDAO: UserDao

    companion object {
        @Volatile
        private var INSTANCE: ItemDatabase? = null
        fun getInstance(context: Context): ItemDatabase{
            synchronized(this){
                var instance = INSTANCE
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,ItemDatabase::class.java, "item_database").fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance as ItemDatabase
            }
        }
    }
}
