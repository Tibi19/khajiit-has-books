package com.tam.tesbooks.data.repository

import com.tam.tesbooks.data.json.JsonLoader
import com.tam.tesbooks.data.json.dto.BookTextDto
import com.tam.tesbooks.data.mapper.toEntities
import com.tam.tesbooks.data.room.database.BookInfoDatabase
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

    suspend fun getBookText(fileName: String): BookTextDto =
        jsonLoader.getBookText(fileName) ?: getEmptyBookTextDto()

}