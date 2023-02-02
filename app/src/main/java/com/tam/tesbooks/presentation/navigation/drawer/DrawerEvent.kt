package com.tam.tesbooks.presentation.navigation.drawer

import com.tam.tesbooks.domain.model.book_list.BookList

sealed class DrawerEvent {
    data class OnCreateNewList(val name: String): DrawerEvent()
    data class OnDeleteList(val bookList: BookList): DrawerEvent()
    data class OnRenameList(val bookList: BookList, val newName: String): DrawerEvent()
}
