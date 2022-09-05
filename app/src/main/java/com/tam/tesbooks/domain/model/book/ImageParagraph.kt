package com.tam.tesbooks.domain.model.book

class ImageParagraph(bookId: String, position: Int, content: String) : BookParagraph(bookId, position, content) {

    lateinit var imageUrl: String
        private set

    override fun parseContent() {
        TODO("Not yet implemented")
    }

}