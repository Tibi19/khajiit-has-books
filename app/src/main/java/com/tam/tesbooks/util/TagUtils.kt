package com.tam.tesbooks.util

import com.tam.tesbooks.domain.model.book.TextAttribute

fun getTagsToTextAttributes(): Map<String, TextAttribute> =
    mapOf(
        TAG_BOLD to TextAttribute.BOLD,
        TAG_ITALIC to TextAttribute.ITALIC,
        TAG_TITLE to TextAttribute.TITLE,
        TAG_HEADER to TextAttribute.HEADER,
        TAG_CENTER to TextAttribute.CENTER,
        TAG_QUOTE to TextAttribute.QUOTE
    )

fun getTextTags(): List<String> =
    listOf(TAG_BOLD, TAG_ITALIC, TAG_TITLE, TAG_HEADER, TAG_CENTER, TAG_QUOTE)

fun getTextTagsRegex(): Regex {
    val tags = getTextTags()
    val tagsWithClosings = tags + tags.map { it.insert(1, "/") }
    return tagsWithClosings.joinToString("|").toRegex()
}

fun getImageUrlRegex(): Regex =
    "(?<=$TAG_IMAGE)(.*)(?=>)".toRegex()