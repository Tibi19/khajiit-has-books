package com.tam.tesbooks.data.json.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookSizesDto(
    @Json(name = "book_sizes")
    val bookSizes: List<BookSizeDto>
)