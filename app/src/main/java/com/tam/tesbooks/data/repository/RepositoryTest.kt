package com.tam.tesbooks.data.repository

import com.tam.tesbooks.data.json.JsonLoader
import com.tam.tesbooks.data.json.dto.BookTextDto
import com.tam.tesbooks.data.mapper.toEntities
import com.tam.tesbooks.data.room.database.BookInfoDatabase
import com.tam.tesbooks.data.room.entity.BookListEntity
import com.tam.tesbooks.data.room.entity.BookMetadataEntity
import com.tam.tesbooks.data.room.query.DynamicMetadataQuery
import com.tam.tesbooks.domain.model.listing_modifier.*
import com.tam.tesbooks.util.*
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

    suspend fun saveDefaultBookLists() {
        val defaultBookListsCount = bookListDao.getDefaultBookListsCount()
        if(defaultBookListsCount >= SIZE_DEFAULT_BOOK_LISTS) return

        val defaultBookLists = listOf(
            BookListEntity(0, DEFAULT_BOOK_LIST_VIEWED, true),
            BookListEntity(0, DEFAULT_BOOK_LIST_READ_LATER, true),
            BookListEntity(0, DEFAULT_BOOK_LIST_FAVORITE, true)
        )
        defaultBookLists.forEach { bookListDao.insertBookList(it) }
    }

    suspend fun getMetadataFromRoom(bookId: Int): BookMetadataEntity = bookMetadataDao.getMetadata(bookId)

    suspend fun getBookText(fileName: String): BookTextDto =
        jsonLoader.getBookText(fileName) ?: getEmptyBookTextDto()

    suspend fun getWithRawQuery(): List<BookMetadataEntity> {
        val libraryOrder = LibraryOrder(Order.SIZE, isReversed = false)
        val libraryFilter = LibraryFilter(categoryFilters = listOf("Pamphlets"))
        val bookListSort = BookListSort(Sort.DATE_ADDED)
        val dynamicMetadataQuery = DynamicMetadataQuery
            .build {
                fromBookList(2)
                sortList(bookListSort)
                filter(libraryFilter)
            }
        // wrap below with try catch
        return bookMetadataDao.getMetadatasWithQuery(dynamicMetadataQuery.rawQuery)
    }

}