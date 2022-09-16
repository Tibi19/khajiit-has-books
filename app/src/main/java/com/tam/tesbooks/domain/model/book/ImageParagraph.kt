package com.tam.tesbooks.domain.model.book

import com.tam.tesbooks.util.FALLBACK_URL_NO_IMAGE
import com.tam.tesbooks.util.getImageUrlRegex

class ImageParagraph(bookId: Int, position: Int, content: String) : BookParagraph(bookId, position, content) {

    lateinit var imageUrl: String
        private set

    init {
        parseContent()
    }

    override fun parseContent() {
        val urlRegex = getImageUrlRegex()
        imageUrl = urlRegex.find(content)?.value ?: FALLBACK_URL_NO_IMAGE
    }

}