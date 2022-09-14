package com.tam.tesbooks.domain.model.metadata

data class BookMetadata(
    val id: Int,
    val title: String,
    val author: String,
    val description: String,
    val tags: List<String>,
    val category: String,
    val fileName: String,
    val textSize: Int
)
