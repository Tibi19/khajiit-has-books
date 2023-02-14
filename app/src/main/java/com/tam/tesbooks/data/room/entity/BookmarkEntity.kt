package com.tam.tesbooks.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tam.tesbooks.data.room.converter.LocalDateTimeConverter
import com.tam.tesbooks.data.room.converter.UuidConverter
import com.tam.tesbooks.util.TABLE_BOOKMARK
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = TABLE_BOOKMARK)
@TypeConverters(LocalDateTimeConverter::class, UuidConverter::class)
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = false)
    val uuid: UUID,
    val bookId: Int,
    val paragraphPosition: Int,
    val paragraphContent: String,
    val bookTitle: String,
    val dateTimeAdded: LocalDateTime
)