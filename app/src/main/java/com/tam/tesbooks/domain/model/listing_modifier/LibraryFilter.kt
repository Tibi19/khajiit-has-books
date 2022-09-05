package com.tam.tesbooks.domain.model.listing_modifier

data class LibraryFilter(
    val categoryFilters: List<String> = listOf(),
    val tagFilters: List<String> = listOf()
)