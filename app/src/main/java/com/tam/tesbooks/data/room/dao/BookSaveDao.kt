package com.tam.tesbooks.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverters
import com.tam.tesbooks.data.room.converter.LocalDateTimeConverter
import com.tam.tesbooks.data.room.entity.BookSaveEntity
import java.time.LocalDateTime

@Dao
interface BookSaveDao {

    @Insert
    suspend fun insertBookSave(bookSaveEntity: BookSaveEntity)

    @Query("DELETE FROM table_book_save WHERE bookId = :bookId AND bookListId = :bookListId")
    suspend fun deleteBookSave(bookId: Int, bookListId: Int)

    @TypeConverters(LocalDateTimeConverter::class)
    @Query("SELECT dateTimeSaved FROM table_book_save WHERE bookId = :bookId AND bookListId = :bookListId LIMIT 1")
    suspend fun getDateTimeSaved(bookId: Int, bookListId: Int): LocalDateTime

}