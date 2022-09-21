package com.tam.tesbooks.data.repository

import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.listing_modifier.BookListSort
import com.tam.tesbooks.domain.model.listing_modifier.LibraryFilter
import com.tam.tesbooks.domain.model.listing_modifier.LibraryOrder
import com.tam.tesbooks.util.Resource
import kotlinx.coroutines.flow.Flow

interface BookInfoRepository {

    suspend fun getBookInfo(id: Int): Resource<BookInfo>

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

}