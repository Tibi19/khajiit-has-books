package com.tam.tesbooks.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tam.tesbooks.util.TABLE_BOOKMARK

@Entity(tableName = TABLE_BOOKMARK)
data class BookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val bookId: String,
    val paragraphPosition: Int
)