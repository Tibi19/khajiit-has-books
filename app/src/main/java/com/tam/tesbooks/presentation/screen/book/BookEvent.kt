package com.tam.tesbooks.presentation.screen.book

import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book.BookParagraph
import com.tam.tesbooks.domain.model.book_list.BookList

sealed class BookEvent {
    data class OnTagSearch(val tag: String): BookEvent()
    data class OnBookSwipe(val swipedToBookIndex: Int): BookEvent()
    data class OnChangeBookList(val bookInfo: BookInfo, val newBookList: BookList): BookEvent()
    data class OnChangeBookmark(val paragraph: BookParagraph): BookEvent()
}