package com.tam.tesbooks.domain.model.bookmark

import com.tam.tesbooks.domain.model.book.BookParagraph

data class Bookmark(
    val id: Int,
    val bookId: String,
    val paragraphPosition: Int,
    val paragraph: BookParagraph
)
