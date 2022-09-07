package com.tam.tesbooks.data.room.entity

import androidx.room.Entity
import com.tam.tesbooks.util.TABLE_BOOK_SAVE

@Entity(
    tableName = TABLE_BOOK_SAVE,
    primaryKeys = ["bookId", "bookListId"]
)
data class BookSaveEntity(
    val bookId: Int,
    val bookListId: Int
)