package com.tam.tesbooks.domain.repository

import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.domain.model.listing_modifier.BookListSort
import com.tam.tesbooks.domain.model.listing_modifier.LibraryFilter
import com.tam.tesbooks.domain.model.listing_modifier.LibraryOrder

interface Repository {

    suspend fun initializeDatabase()

    suspend fun getBookInfos(
        libraryOrder: LibraryOrder,
        libraryFilter: LibraryFilter,
        alreadyLoadedInfos: List<BookInfo> = emptyList(),
        searchQuery: String = ""
    ): List<BookInfo>?

    suspend fun getBookInfosFromList(
        bookList: BookList,
        bookListSort: BookListSort,
        alreadyLoadedInfos: List<BookInfo> = emptyList(),
    ): List<BookInfo>?

    suspend fun getBook(bookInfo: BookInfo): Book

    suspend fun getBookLists(): List<BookList>

    suspend fun addBookList(bookList: BookList)

    suspend fun editBookList(bookList: BookList)

    suspend fun removeBookList(bookList: BookList)

    suspend fun addBookToList(bookInfo: BookInfo, bookList: BookList)

    suspend fun removeBookFromList(bookInfo: BookInfo, bookList: BookList)

    suspend fun getBookmarks(lastLoadedBookmark: Bookmark? = null): List<Bookmark>

    suspend fun addBookmark(bookmark: Bookmark)

    suspend fun removeBookmark(bookmark: Bookmark)

    suspend fun getCategories(): List<String>

    suspend fun getTags(): List<String>

}