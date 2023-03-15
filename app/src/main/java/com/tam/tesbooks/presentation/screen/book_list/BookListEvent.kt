package com.tam.tesbooks.presentation.screen.book_list

import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.listing_modifier.BookListSort

sealed class BookListEvent {
    object OnLoadMoreBookInfos: BookListEvent()
    data class OnChangeBookList(val bookInfo: BookInfo, val bookList: BookList): BookListEvent()
    data class OnSortChange(val bookListSort: BookListSort): BookListEvent()
}
