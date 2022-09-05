package com.tam.tesbooks.domain.model.book

abstract class BookParagraph(
    val bookId: String,
    val position: Int,
    val content: String
) {

    init{
        this.parseContent()
    }

    abstract fun parseContent()

}