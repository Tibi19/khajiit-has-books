package com.tam.tesbooks.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_book_list")
data class BookListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val isDefault: Boolean = false
)