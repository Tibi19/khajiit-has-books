package com.tam.tesbooks.presentation.dialog.lists

import com.tam.tesbooks.domain.model.book_list.BookList

data class ListsState(
    val bookLists: List<BookList> = emptyList()
)
