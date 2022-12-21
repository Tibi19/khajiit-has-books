package com.tam.tesbooks.domain.repository

import com.tam.tesbooks.domain.model.book.BookInfo

sealed class ChangingData {
    object Bookmarks: ChangingData()
    object BookLists: ChangingData()
    data class BookSavedInLists(val bookInfo: BookInfo): ChangingData()
}
