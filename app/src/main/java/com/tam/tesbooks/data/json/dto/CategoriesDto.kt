package com.tam.tesbooks.data.json.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoriesDto(
    val categories: List<String>
)