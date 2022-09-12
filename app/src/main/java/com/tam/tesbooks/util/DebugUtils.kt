package com.tam.tesbooks.util

import android.util.Log.d
import com.tam.tesbooks.data.json.dto.BookMetadataDto
import com.tam.tesbooks.data.json.dto.BookTextDto
import com.tam.tesbooks.data.room.entity.BookMetadataEntity
import com.tam.tesbooks.domain.model.metadata.BookMetadata

const val TEST_TAG = "TEST_TES"

fun printTest(message: String) = d(TEST_TAG, message)

fun printMetadataTest(book: BookMetadataDto) = printTest("Book id ${book.id}: ${book.title} by ${book.author}")
fun printMetadataTest(book: BookMetadataEntity) = printTest("Book id ${book.id}: ${book.title} by ${book.author}")
fun printMetadataTest(book: BookMetadata) = printTest("Book id ${book.id}: ${book.title} by ${book.author}")

fun getEmptyMetadataDto() =
    BookMetadataDto(
        id = -1,
        title = "No Title",
        author = "No Author",
        description = "No Description",
        tags = emptyList(),
        category = "No Category",
        fileName = "No file name",
        textSize = 0
    )

fun getEmptyMetadataEntity() =
    BookMetadataEntity(
        id = -1,
        title = "No Title",
        author = "No Author",
        description = "No Description",
        tags = emptyList(),
        category = "No Category",
        fileName = "No file name",
        textSize = 0
    )

fun getEmptyBookTextDto() =
    BookTextDto(
        id = -1,
        paragraphs = emptyList()
    )