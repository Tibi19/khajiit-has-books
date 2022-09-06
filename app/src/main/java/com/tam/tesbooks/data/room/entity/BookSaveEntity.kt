package com.tam.tesbooks.data.room.entity

import androidx.room.Entity

@Entity(
    tableName = "table_book_save",
    primaryKeys = ["bookId", "bookListId"]
)
data class BookSaveEntity(
    val bookId: String,
    val bookListId: Int
)