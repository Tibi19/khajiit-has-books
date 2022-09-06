package com.tam.tesbooks.data.json.dto

import com.squareup.moshi.Json

data class BookSizeDto(
    val id: Int,
    @field:Json(name = "book_size")
    val bookSize: Int
)