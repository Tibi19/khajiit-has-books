package com.tam.tesbooks.presentation.screen.book

import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.bookmark.Bookmark

data class BookState(
    val isLoading: Boolean = false,
    val currentBookIndex: Int = 0,
    val booksStack: List<Book> = emptyList(),
    val bookmarks: List<Bookmark> = emptyList(),
    val bookLists: List<BookList> = emptyList()
) {
    val curentBook: Book?
        get() {
            if (booksStack.isEmpty()) return null
            return booksStack[currentBookIndex]
        }
}
