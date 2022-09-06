package com.tam.tesbooks.data.json.dto

data class BookMetadataDto(
    val id: Int,
    val title: String,
    val author: String,
    val description: String,
    val tags: List<String>,
    val category: String,
    val fileName: String
)