package com.tam.tesbooks.domain.model.book

abstract class BookParagraph(
    val bookId: Int,
    val position: Int,
    val content: String
) {

    abstract fun parseContent()

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (other !is BookParagraph) return false
        return this.hashCode() == other.hashCode()
    }

    override fun hashCode(): Int {
        var result = bookId
        result = 31 * result + position
        result = 31 * result + content.hashCode()
        return result
    }
}