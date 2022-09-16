package com.tam.tesbooks.data.mapper

import com.tam.tesbooks.domain.converter.toBookParagraphs
import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.domain.model.book.BookInfo

fun BookInfo.toBookWithContents(contents: List<String>): Book =
    Book(
        bookInfo = this,
        paragraphs = contents.toBookParagraphs(bookId)
    )