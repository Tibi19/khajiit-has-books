package com.tam.tesbooks.domain.model.book

import com.tam.tesbooks.util.getTagsToTextAttributes
import com.tam.tesbooks.util.getTextTagsRegex

class TextParagraph(bookId: Int, position: Int, content: String): BookParagraph(bookId, position, content) {

    private val attributes: MutableList<TextAttribute> = mutableListOf()

    val isBold get() = attributes.contains(TextAttribute.BOLD)
    val isItalic get() = attributes.contains(TextAttribute.ITALIC)
    val isHeader get() = attributes.contains(TextAttribute.HEADER)
    val isTitle get() = attributes.contains(TextAttribute.TITLE)
    val isCentered get() = attributes.contains(TextAttribute.CENTER)
    val isQuote get() = attributes.contains(TextAttribute.QUOTE)

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
            attributes.add(textToAttribute.value)
        }

}