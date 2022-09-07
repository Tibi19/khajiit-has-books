package com.tam.tesbooks.data.json.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BookTextDto(
    val id: Int,
    val paragraphs: List<String>
)