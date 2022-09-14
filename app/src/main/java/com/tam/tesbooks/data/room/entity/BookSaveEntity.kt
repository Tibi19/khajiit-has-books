package com.tam.tesbooks.data.room.entity

import androidx.room.Entity
import androidx.room.TypeConverters
import com.tam.tesbooks.data.room.converter.LocalDateTimeConverter
import com.tam.tesbooks.util.TABLE_BOOK_SAVE
import java.time.LocalDateTime

@Entity(
    tableName = TABLE_BOOK_SAVE,
    primaryKeys = ["bookId", "bookListId"]
)
@TypeConverters(LocalDateTimeConverter::class)
data class BookSaveEntity(
    val bookId: Int,
    val bookListId: Int,
    val dateTimeSaved: LocalDateTime
)