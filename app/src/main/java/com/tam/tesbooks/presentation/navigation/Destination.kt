package com.tam.tesbooks.presentation.navigation

const val ARG_TAG = "tag"
const val ARG_BOOK_LIST_ID = "book_list_id"
const val ARG_BOOK_ID = "book_id"

sealed class Destination(val route: String) {
    object Library: Destination("library?$ARG_TAG={$ARG_TAG}") {
        fun createRoute(tag: String) = "library?$ARG_TAG=$tag"
    }

    object BookList: Destination("book_list/{$ARG_BOOK_LIST_ID}") {
        fun createRoute(bookListId: Int) = "book_list/$bookListId"
    }

    object Book: Destination("book/{$ARG_BOOK_ID}") {
        fun createRoute(bookId: Int) = "book/$bookId"
    }

    object Bookmarks: Destination("bookmarks")
    object Settings: Destination("settings")
}

fun getDestinationsWithBottomBar(): List<Destination> =
    listOf(
        Destination.Library,
        Destination.BookList,
        Destination.Bookmarks,
        Destination.Settings
    )
