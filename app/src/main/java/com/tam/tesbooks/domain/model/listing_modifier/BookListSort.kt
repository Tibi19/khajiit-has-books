package com.tam.tesbooks.domain.model.listing_modifier

data class BookListSort (
    val sortBy: Sort = Sort.DATE_ADDED,
    val isReversed: Boolean = false
)