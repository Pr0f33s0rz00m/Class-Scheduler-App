package com.example.newapp.ui.login.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
@Entity(tableName = "item_table")
public data class Item(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,

        @NotNull
        @ColumnInfo(name = "className")
        var className: String = "",
        @NotNull
        @ColumnInfo(name = "classDetails")
        var classDetails: String = "",
        @NotNull
        @ColumnInfo(name = "date")
        var date: String = "",

)
