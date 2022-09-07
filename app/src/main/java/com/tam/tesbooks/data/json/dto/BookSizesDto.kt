package com.tam.tesbooks.data.json.dto

import com.squareup.moshi.Json

data class BookSizesDto(
    @field:Json(name = "book_sizes")
    val bookSizes: List<BookSizeDto>
)