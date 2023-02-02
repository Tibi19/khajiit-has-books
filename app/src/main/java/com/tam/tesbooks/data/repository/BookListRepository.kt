package com.tam.tesbooks.data.repository

import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.util.Resource

interface BookListRepository {

    suspend fun saveDefaultBookLists()

    suspend fun getBookLists(): Resource<List<BookList>>

    suspend fun addBookList(bookList: BookList)

    suspend fun editBookList(bookList: BookList)

    suspend fun removeBookList(bookList: BookList)

    suspend fun addBookToList(bookInfo: BookInfo, bookList: BookList)

    suspend fun removeBookFromList(bookInfo: BookInfo, bookList: BookList)

    suspend fun getBookSavesCountInList(bookList: BookList): Resource<Int>

}