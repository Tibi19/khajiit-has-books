package com.tam.tesbooks.domain.model.book

class TextParagraph(bookId: Int, position: Int, content: String): BookParagraph(bookId, position, content) {

    private val _attributes: MutableList<TextAttribute> = mutableListOf()
    val attributes: List<TextAttribute> = _attributes

    override fun parseContent() {
        TODO("Not yet implemented")
    }

}