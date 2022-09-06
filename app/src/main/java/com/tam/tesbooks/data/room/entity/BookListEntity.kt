package com.tam.tesbooks.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tam.tesbooks.util.TABLE_BOOK_LIST

@Entity(tableName = TABLE_BOOK_LIST)
data class BookListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val isDefault: Boolean = false
)