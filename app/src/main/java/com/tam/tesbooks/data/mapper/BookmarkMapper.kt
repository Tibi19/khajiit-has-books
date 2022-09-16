package com.tam.tesbooks.data.mapper

import com.tam.tesbooks.data.room.entity.BookmarkEntity
import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.domain.converter.toBookParagraph

fun List<BookmarkEntity>.toBookmarks(): List<Bookmark> =
    map { it.toBookmark() }

fun BookmarkEntity.toBookmark(): Bookmark =
    Bookmark(
        id = id,
        paragraph = paragraphContent.toBookParagraph(bookId, paragraphPosition),
        dateTimeAdded = dateTimeAdded,
    )

fun Bookmark.toEntity(): BookmarkEntity =
    BookmarkEntity(
        id = id,
        bookId = paragraph.bookId,
        paragraphPosition = paragraph.position,
        paragraphContent = paragraph.content,
        dateTimeAdded = dateTimeAdded
    )