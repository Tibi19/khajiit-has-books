package com.tam.tesbooks.presentation.screen.library

import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.listing_modifier.LibraryFilter
import com.tam.tesbooks.domain.model.listing_modifier.LibraryOrder

data class LibraryState(
    val bookInfos: List<BookInfo> = emptyList(),
    val bookLists: List<BookList> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val libraryOrder: LibraryOrder = LibraryOrder(),
    val libraryFilter: LibraryFilter = LibraryFilter()
)