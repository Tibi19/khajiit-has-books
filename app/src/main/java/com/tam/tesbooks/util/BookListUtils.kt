package com.tam.tesbooks.util

import com.tam.tesbooks.domain.model.book_list.BookList

fun List<BookList>.getViewed() = this.find { it.name == DEFAULT_BOOK_LIST_VIEWED } ?: BookListUtils.emptyBookList
fun List<BookList>.getReadLater() = this.find { it.name == DEFAULT_BOOK_LIST_READ_LATER } ?: BookListUtils.emptyBookList
fun List<BookList>.getFavorite() = this.find { it.name == DEFAULT_BOOK_LIST_FAVORITE } ?: BookListUtils.emptyBookList

object BookListUtils {
    val emptyBookList = BookList("", false, -1)
}