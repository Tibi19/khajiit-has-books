package com.tam.tesbooks.domain.model.bookmark

import com.tam.tesbooks.domain.model.book.BookParagraph

data class Bookmark(
    val id: Int,
    val bookId: Int,
    val paragraphPosition: Int,
    val paragraph: BookParagraph
)
