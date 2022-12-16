package com.tam.tesbooks.presentation.navigation

import com.tam.tesbooks.util.toRouteArg

const val ARG_TAG = "tag"
const val ARG_CATEGORY = "category"
const val ARG_BOOK_LIST_ID = "book_list_id"
const val ARG_BOOK_ID = "book_id"

sealed class Destination(val route: String) {
    object Library: Destination("library?$ARG_TAG={$ARG_TAG}&$ARG_CATEGORY={$ARG_CATEGORY}") {
        fun createRoute(tag: String = "", category: String = ""): String {
            val isTagNotEmpty = tag.isNotEmpty()
            val isCategoryNotEmpty = category.isNotEmpty()

            val questionMark = if (isTagNotEmpty || isCategoryNotEmpty) "?" else ""
            val andSign = if (isTagNotEmpty && isCategoryNotEmpty) "&" else ""
            val optionalTag = if (isTagNotEmpty) "$ARG_TAG=${tag.toRouteArg()}" else ""
            val optionalCategory = if (isCategoryNotEmpty) "$ARG_CATEGORY=${category.toRouteArg()}" else ""

            return "library$questionMark$optionalTag$andSign$optionalCategory"
        }
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
