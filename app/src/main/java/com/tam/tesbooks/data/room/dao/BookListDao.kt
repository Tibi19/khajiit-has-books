package com.tam.tesbooks.data.room.dao

import androidx.room.*
import com.tam.tesbooks.data.room.entity.BookListEntity
import com.tam.tesbooks.data.room.relations.BookListWithBookSaves

@Dao
interface BookListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookList(bookListEntity: BookListEntity)

    @Query("SELECT * FROM table_book_list")
    suspend fun getBookLists(): List<BookListEntity>

    @Query("DELETE FROM table_book_list WHERE id = :bookListId")
    suspend fun deleteBookList(bookListId: Int)

    @Update
    suspend fun updateBookList(bookListEntity: BookListEntity)

    @Transaction
    @Query("SELECT * FROM table_book_list WHERE id = :bookListId")
    suspend fun getBookListWithBookSaves(bookListId: Int): BookListWithBookSaves

    @Query("SELECT * FROM table_book_list INNER JOIN table_book_save " +
            "ON table_book_list.id = table_book_save.bookListId " +
            "WHERE table_book_save.bookId = :bookId")
    suspend fun getBookListsOfBookId(bookId: Int): List<BookListEntity>

    @Query("SELECT COUNT(id) FROM table_book_list WHERE isDefault = 1")
    suspend fun getDefaultBookListsCount(): Int

}