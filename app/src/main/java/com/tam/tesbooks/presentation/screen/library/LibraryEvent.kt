package com.tam.tesbooks.presentation.screen.library

import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.listing_modifier.LibraryFilter
import com.tam.tesbooks.domain.model.listing_modifier.LibraryOrder

sealed class LibraryEvent {
    object OnLoadMoreBookInfos: LibraryEvent()
    data class OnChangeBookList(val bookInfo: BookInfo, val bookList: BookList): LibraryEvent()
    data class OnSearchQueryChange(val query: String): LibraryEvent()
    data class OnOrderChange(val newLibraryOrder: LibraryOrder): LibraryEvent()
    data class OnFilterChange(val newLibraryFilter: LibraryFilter): LibraryEvent()
}
