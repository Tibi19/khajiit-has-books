package com.tam.tesbooks.data.mapper

import com.tam.tesbooks.data.room.entity.BookListEntity
import com.tam.tesbooks.domain.model.book_list.BookList

fun List<BookListEntity>.toBookLists(): List<BookList> = map { it.toBookList() }

fun BookListEntity.toBookList() =
    BookList(
        id = id,
        name = name,
        isDefault = isDefault
    )
