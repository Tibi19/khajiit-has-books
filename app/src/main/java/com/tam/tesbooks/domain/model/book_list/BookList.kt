package com.tam.tesbooks.domain.model.book_list

data class BookList (
    val name: String,
    val isDefault: Boolean = false,
    val id: Int = 0
)