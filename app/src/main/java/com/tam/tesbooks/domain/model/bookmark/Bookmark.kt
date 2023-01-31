package com.tam.tesbooks.domain.model.bookmark

import com.tam.tesbooks.domain.model.book.BookParagraph
import java.time.LocalDateTime
import java.util.*

data class Bookmark(
    val uuid: UUID,
    val paragraph: BookParagraph,
    val dateTimeAdded: LocalDateTime
)
