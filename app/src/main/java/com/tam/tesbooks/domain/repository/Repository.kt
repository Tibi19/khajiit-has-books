package com.tam.tesbooks.domain.repository

import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.domain.model.listing_modifier.BookListSort
import com.tam.tesbooks.domain.model.listing_modifier.LibraryFilter
import com.tam.tesbooks.domain.model.listing_modifier.LibraryOrder
import com.tam.tesbooks.util.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun initializeDatabase(): Resource<Unit>

    suspend fun getBookInfos(
        libraryOrder: LibraryOrder,
        libraryFilter: LibraryFilter,
        alreadyLoadedInfos: List<BookInfo> = emptyList(),
        searchQuery: String = ""
    ): Flow<Resource<List<BookInfo>>>

    suspend fun getBookInfosFromList(
        bookList: BookList,
        bookListSort: BookListSort,
        alreadyLoadedInfos: List<BookInfo> = emptyList(),
    ): Flow<Resource<List<BookInfo>>>

    suspend fun getBookLists(): Resource<List<BookList>>

    suspend fun addBookList(bookList: BookList)

    suspend fun editBookList(bookList: BookList)

    suspend fun removeBookList(bookList: BookList)

    suspend fun addBookToList(bookInfo: BookInfo, bookList: BookList)

    suspend fun removeBookFromList(bookInfo: BookInfo, bookList: BookList)

    suspend fun getBookmarks(lastLoadedBookmark: Bookmark? = null): Flow<Resource<List<Bookmark>>>

    suspend fun getBookmarksOfBookId(bookId: Int): Flow<Resource<List<Bookmark>>>

    suspend fun addBookmark(bookmark: Bookmark)

    suspend fun removeBookmark(bookmark: Bookmark)

    suspend fun getBook(bookInfo: BookInfo): Flow<Resource<Book>>

    suspend fun getCategories(): Resource<List<String>>

    suspend fun getTags(): Resource<List<String>>

}