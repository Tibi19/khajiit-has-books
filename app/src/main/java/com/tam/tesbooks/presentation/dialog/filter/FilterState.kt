package com.tam.tesbooks.presentation.dialog.filter

data class FilterState(
    val tags: List<String> = emptyList(),
    val categories: List<String> = emptyList()
)