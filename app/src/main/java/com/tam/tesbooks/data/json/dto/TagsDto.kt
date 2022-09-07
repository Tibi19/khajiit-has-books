package com.tam.tesbooks.data.json.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TagsDto(
    val tags: List<String>
)