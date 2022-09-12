package com.tam.tesbooks.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tam.tesbooks.data.room.converter.TagsConverter
import com.tam.tesbooks.util.TABLE_BOOK_METADATA

@Entity(tableName = TABLE_BOOK_METADATA)
@TypeConverters(TagsConverter::class)
data class BookMetadataEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val author: String,
    val description: String,
    val tags: List<String>,
    val category: String,
    val fileName: String,
    val textSize: Int
)