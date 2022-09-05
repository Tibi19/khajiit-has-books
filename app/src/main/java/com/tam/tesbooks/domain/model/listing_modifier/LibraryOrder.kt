package com.tam.tesbooks.domain.model.listing_modifier

data class LibraryOrder (
    val orderBy: Order? = null,
    val isReversed: Boolean = false
)