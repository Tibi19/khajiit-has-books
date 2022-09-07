package com.tam.tesbooks.domain.model.book

import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.metadata.BookMetadata

data class BookInfo(
    val bookId: Int,
    val bookMetadata: BookMetadata,
    val savedInBookLists: List<BookList>
)