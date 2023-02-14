package com.tam.tesbooks.data.mapper

import com.tam.tesbooks.data.room.entity.BookmarkEntity
import com.tam.tesbooks.domain.converter.toBookParagraph
import com.tam.tesbooks.domain.model.bookmark.Bookmark

fun List<BookmarkEntity>.toBookmarks(): List<Bookmark> =
    map { it.toBookmark() }

fun BookmarkEntity.toBookmark(): Bookmark =
    Bookmark(
        uuid = uuid,
        paragraph = paragraphContent.toBookParagraph(bookId, paragraphPosition),
        bookTitle = bookTitle,
        dateTimeAdded = dateTimeAdded,
    )

fun Bookmark.toEntity(): BookmarkEntity =
    BookmarkEntity(
        uuid = uuid,
        bookId = paragraph.bookId,
        paragraphPosition = paragraph.position,
        paragraphContent = paragraph.content,
        bookTitle = bookTitle,
        dateTimeAdded = dateTimeAdded
    )