package com.tam.tesbooks.data.mapper

import com.tam.tesbooks.data.room.entity.BookListEntity
import com.tam.tesbooks.data.room.entity.BookMetadataEntity
import com.tam.tesbooks.domain.model.book.BookInfo

inline fun List<BookMetadataEntity>.toBookInfosWithLists(getBookLists: BookMetadataEntity.() -> List<BookListEntity>): List<BookInfo> =
    map { it.toBookInfo(it.getBookLists()) }

fun BookMetadataEntity.toBookInfo(bookListEntities: List<BookListEntity>): BookInfo =
    BookInfo(
        bookId = id,
        metadata = toMetadata(),
        savedInBookLists = bookListEntities.toBookLists()
    )