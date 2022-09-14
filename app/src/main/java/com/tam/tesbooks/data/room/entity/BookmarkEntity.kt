package com.tam.tesbooks.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tam.tesbooks.data.room.converter.LocalDateTimeConverter
import com.tam.tesbooks.util.TABLE_BOOKMARK
import java.time.LocalDateTime

@Entity(tableName = TABLE_BOOKMARK)
@TypeConverters(LocalDateTimeConverter::class)
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bookId: Int,
    val paragraphPosition: Int,
    val dateTimeAdded: LocalDateTime
)