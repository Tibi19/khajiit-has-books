package com.tam.tesbooks.domain.model.book

import com.tam.tesbooks.util.getTagsToTextAttributes
import com.tam.tesbooks.util.getTextTagsRegex

class TextParagraph(bookId: Int, position: Int, content: String): BookParagraph(bookId, position, content) {

    private val _attributes: MutableList<TextAttribute> = mutableListOf()
    val attributes: List<TextAttribute> = _attributes

    lateinit var text: String
        private set

    init {
        parseContent()
    }

    override fun parseContent() {
        parseCleanText()
        parseAttributes()
    }

    private fun parseCleanText() {
        text = content.replace(getTextTagsRegex(), "")
    }

    private fun parseAttributes() =
        getTagsToTextAttributes().forEach { textToAttribute ->
            if(!content.contains(textToAttribute.key)) return@forEach
            _attributes.add(textToAttribute.value)
        }

}