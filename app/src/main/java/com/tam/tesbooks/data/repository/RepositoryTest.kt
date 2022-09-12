package com.tam.tesbooks.data.repository

import com.tam.tesbooks.data.json.JsonLoader
import com.tam.tesbooks.data.json.dto.BookTextDto
import com.tam.tesbooks.data.mapper.toEntities
import com.tam.tesbooks.data.room.database.BookInfoDatabase
import com.tam.tesbooks.data.room.entity.BookMetadataEntity
import com.tam.tesbooks.data.room.raw_query.BookMetadataQuery
import com.tam.tesbooks.domain.model.listing_modifier.LibraryOrder
import com.tam.tesbooks.domain.model.listing_modifier.Order
import com.tam.tesbooks.util.SIZE_BOOK_METADATAS
import com.tam.tesbooks.util.getEmptyBookTextDto
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryTest @Inject constructor(
    private val database: BookInfoDatabase,
    private val jsonLoader: JsonLoader
) {

    private val bookListDao = database.bookListDao
    private val bookmarkDao = database.bookmarkDao
    private val bookSaveDao = database.bookSaveDao
    private val bookMetadataDao = database.bookMetadataDao

    suspend fun saveMetadatas() {
        val metadatasCount = bookMetadataDao.getMetadatasCount()
        if(metadatasCount >= SIZE_BOOK_METADATAS) return

        val metadatas = jsonLoader.getBooksMetadataDto()?.toEntities() ?: return
        bookMetadataDao.insertAllMetadatas(metadatas)
    }

    suspend fun getMetadataFromRoom(bookId: Int): BookMetadataEntity = bookMetadataDao.getMetadata(bookId)

    suspend fun getBookText(fileName: String): BookTextDto =
        jsonLoader.getBookText(fileName) ?: getEmptyBookTextDto()

    suspend fun getWithRawQuery(): List<BookMetadataEntity> {
        val libraryOrder = LibraryOrder(Order.SIZE)
        val query = BookMetadataQuery
            .build {
                order(libraryOrder)
                after(458707)
                except(listOf(2112, 4604))
            }
        return bookMetadataDao.getWithQuery(query.rawQuery)
    }

}