package com.tam.tesbooks.presentation.navigation.drawer

import com.tam.tesbooks.domain.model.book_list.BookList

data class DrawerState(
    val bookLists: List<BookList> = emptyList(),
    val defaultListNameToBookCountMap: Map<String, Int> = emptyMap()
)
