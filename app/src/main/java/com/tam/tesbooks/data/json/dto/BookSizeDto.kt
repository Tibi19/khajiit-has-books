package com.tam.tesbooks.data.json.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookSizeDto(
    val id: Int,
    @Json(name = "book_size")
    val bookSize: Int
)