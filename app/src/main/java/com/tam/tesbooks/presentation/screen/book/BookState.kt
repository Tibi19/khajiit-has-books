package com.tam.tesbooks.presentation.screen.book

import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.domain.model.bookmark.Bookmark

data class BookState(
    val book: Book? = null,
    val isLoading: Boolean = false,
    val bookHistory: List<Book> = emptyList(),
    val nextRandomBook: Book? = null,
    val bookmarks: List<Bookmark> = emptyList()
)
