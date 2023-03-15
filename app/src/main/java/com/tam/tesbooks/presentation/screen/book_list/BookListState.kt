package com.tam.tesbooks.presentation.screen.book_list

import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.listing_modifier.BookListSort

data class BookListState(
    val bookList: BookList? = null,
    val bookLists: List<BookList> = emptyList(),
    val bookInfos: List<BookInfo> = emptyList(),
    val isLoading: Boolean = false,
    val canLoadMore: Boolean = false,
    val pagesLoaded: Int = 0,
    val listSort: BookListSort = BookListSort()
)
