package com.tam.tesbooks.domain.converter

import com.tam.tesbooks.domain.model.book.BookParagraph
import com.tam.tesbooks.domain.model.book.ImageParagraph
import com.tam.tesbooks.domain.model.book.TextParagraph
import com.tam.tesbooks.util.TAG_IMAGE

fun String.toBookParagraph(bookId: Int, paragraphPosition: Int): BookParagraph {
    if(contains(TAG_IMAGE)) {
        return ImageParagraph(bookId, paragraphPosition, this)
    }
    return TextParagraph(bookId, paragraphPosition, this)
}