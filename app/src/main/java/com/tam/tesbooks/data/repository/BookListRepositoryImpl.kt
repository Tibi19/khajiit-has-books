package com.tam.tesbooks.data.repository

import com.tam.tesbooks.data.mapper.toBookLists
import com.tam.tesbooks.data.mapper.toEntity
import com.tam.tesbooks.data.room.database.BookInfoDatabase
import com.tam.tesbooks.data.room.entity.BookListEntity
import com.tam.tesbooks.data.room.entity.BookSaveEntity
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.util.*
import java.time.LocalDateTime
import javax.inject.Inject

class BookListRepositoryImpl @Inject constructor(
    database: BookInfoDatabase
): BookListRepository {

    private val bookListDao = database.bookListDao
    private val bookSaveDao = database.bookSaveDao

    override suspend fun saveDefaultBookLists() {
        val defaultBookListsCount = bookListDao.getDefaultBookListsCount()
        if(defaultBookListsCount >= SIZE_DEFAULT_BOOK_LISTS) return

        val defaultBookLists = listOf(
            BookListEntity(0, DEFAULT_BOOK_LIST_VIEWED, true),
            BookListEntity(0, DEFAULT_BOOK_LIST_FAVORITE, true),
            BookListEntity(0, DEFAULT_BOOK_LIST_READ_LATER, true)
        )
        defaultBookLists.forEach { bookListDao.insertBookList(it) }
    }

    override suspend fun getBookLists(): Resource<List<BookList>> {
        val defaultBookListsCount = bookListDao.getDefaultBookListsCount()
        if (defaultBookListsCount < SIZE_DEFAULT_BOOK_LISTS) {
            return Resource.Error(ERROR_NO_DEFAULT_LISTS)
        }

        val bookListEntities = bookListDao.getBookLists()
        return Resource.Success(bookListEntities.toBookLists())
    }

    override suspend fun addBookList(bookList: BookList) =
        bookListDao.insertBookList(bookList.toEntity())

    override suspend fun editBookList(bookList: BookList) =
        bookListDao.updateBookList(bookList.toEntity())

    override suspend fun removeBookList(bookList: BookList) {
        if (bookList.isDefault) return
        bookListDao.deleteBookList(bookList.id)
    }

    override suspend fun addBookToList(bookInfo: BookInfo, bookList: BookList) =
        bookSaveDao.insertBookSave(
            BookSaveEntity(bookInfo.bookId, bookList.id, LocalDateTime.now())
        )

    override suspend fun removeBookFromList(bookInfo: BookInfo, bookList: BookList) =
        bookSaveDao.deleteBookSave(bookInfo.bookId, bookList.id)

    override suspend fun getBookSavesCountInList(bookList: BookList): Resource<Int> {
        val bookSavesCount = bookSaveDao.getBookSavesCountInList(bookList.id)
        return Resource.Success(bookSavesCount)
    }
}