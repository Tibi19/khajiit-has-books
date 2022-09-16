package com.tam.tesbooks.domain.model.book

data class Book(
    val bookInfo: BookInfo,
    val paragraphs: List<BookParagraph>
)
