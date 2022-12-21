package com.tam.tesbooks.presentation.dialog.lists

import com.tam.tesbooks.domain.model.book_list.BookList

sealed class ListsEvent {
    data class OnCreateNewList(val name: String): ListsEvent()
    data class OnDeleteList(val bookList: BookList): ListsEvent()
}
