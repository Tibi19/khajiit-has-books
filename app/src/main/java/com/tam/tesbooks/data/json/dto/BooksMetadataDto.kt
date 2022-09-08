package com.tam.tesbooks.data.json.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.tam.tesbooks.domain.model.metadata.BookMetadata

@JsonClass(generateAdapter = true)
data class BooksMetadataDto(
    @Json(name = "books_metadata")
    val booksMetadata: List<BookMetadataDto>
)