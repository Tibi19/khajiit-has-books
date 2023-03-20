package com.tam.tesbooks.domain.repository

sealed class ChangingData {
    object Bookmarks: ChangingData()
    object BookLists: ChangingData()
    data class BookSavedInLists(val bookInfoId: Int): ChangingData()
}
