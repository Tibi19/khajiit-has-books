package com.tam.tesbooks.domain.model.bookmark

import com.tam.tesbooks.domain.model.book.BookParagraph
import java.time.LocalDateTime

data class Bookmark(
    val id: Int,
    val bookId: Int,
    val paragraphPosition: Int,
    val paragraph: BookParagraph,
    val dateTimeAdded: LocalDateTime
)
