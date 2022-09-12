package com.tam.tesbooks.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tam.tesbooks.data.room.dao.BookListDao
import com.tam.tesbooks.data.room.dao.BookMetadataDao
import com.tam.tesbooks.data.room.dao.BookSaveDao
import com.tam.tesbooks.data.room.dao.BookmarkDao
import com.tam.tesbooks.data.room.entity.BookListEntity
import com.tam.tesbooks.data.room.entity.BookMetadataEntity
import com.tam.tesbooks.data.room.entity.BookSaveEntity
import com.tam.tesbooks.data.room.entity.BookmarkEntity

@Database(
    entities = [
        BookListEntity::class,
        BookmarkEntity::class,
        BookSaveEntity::class,
        BookMetadataEntity::class
    ],
    version = 2
)
abstract class BookInfoDatabase: RoomDatabase() {
    abstract val bookListDao: BookListDao
    abstract val bookmarkDao: BookmarkDao
    abstract val bookSaveDao: BookSaveDao
    abstract val bookMetadataDao: BookMetadataDao
}