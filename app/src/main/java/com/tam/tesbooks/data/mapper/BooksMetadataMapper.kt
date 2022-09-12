package com.tam.tesbooks.data.mapper

import com.tam.tesbooks.data.json.dto.BookMetadataDto
import com.tam.tesbooks.data.json.dto.BooksMetadataDto
import com.tam.tesbooks.data.room.entity.BookMetadataEntity

fun BooksMetadataDto.toEntities() =
    booksMetadata.map { it.toEntity() }

fun BookMetadataDto.toEntity() =
    BookMetadataEntity(
        id = id,
        title = title,
        author = author,
        description = description,
        tags = tags,
        category = category,
        fileName = fileName,
        textSize = textSize
    )