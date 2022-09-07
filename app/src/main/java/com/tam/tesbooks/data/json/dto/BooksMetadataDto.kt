package com.tam.tesbooks.data.json.dto

import com.squareup.moshi.JsonClass
import com.tam.tesbooks.domain.model.metadata.BookMetadata

@JsonClass(generateAdapter = true)
data class BooksMetadataDto(
    val booksMetadata: List<BookMetadata>
)