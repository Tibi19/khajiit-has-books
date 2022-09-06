package com.tam.tesbooks.data.json.dto

import com.tam.tesbooks.domain.model.metadata.BookMetadata

data class BooksMetadataDto(
    val booksMetadata: List<BookMetadata>
)