package com.tam.tesbooks.presentation.screen.book

import com.tam.tesbooks.domain.model.book.BookParagraph
import com.tam.tesbooks.presentation.navigation.Destination

sealed class BookEvent {
    data class OnTagSearch(val tag: String): BookEvent()
    data class OnBookSwipe(val swipeDirection: Int): BookEvent()
    data class OnChangeBookLists(val newBookLists: List<Destination.BookList>): BookEvent()
    data class OnCreateList(val name: String): BookEvent()
    data class OnAddBookmark(val paragraph: BookParagraph): BookEvent()
    data class OnRemoveBookmark(val paragraph: BookParagraph): BookEvent()
}
