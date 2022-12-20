package com.tam.tesbooks.presentation.screen.book

import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.domain.model.bookmark.Bookmark

data class BookState(
    val isLoading: Boolean = false,
    val booksStack: List<Book> = emptyList(),
    val bookmarks: List<Bookmark> = emptyList()
)
