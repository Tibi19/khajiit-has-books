package com.tam.tesbooks.data.repository

import com.tam.tesbooks.data.json.JsonLoader
import com.tam.tesbooks.data.mapper.toBookInfo
import com.tam.tesbooks.data.mapper.toBookWithContents
import com.tam.tesbooks.data.mapper.toEntities
import com.tam.tesbooks.data.room.database.BookInfoDatabase
import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class JsonRepositoryImpl @Inject constructor(
    database: BookInfoDatabase,
    private val jsonLoader: JsonLoader
): JsonRepository {

    private val bookMetadataDao = database.bookMetadataDao
    private val bookListDao = database.bookListDao

    override suspend fun saveMetadatasFromJson(): Resource<Unit> {
        val metadatasCount = bookMetadataDao.getMetadatasCount()
        if(metadatasCount >= SIZE_BOOK_METADATAS) return Resource.Success()

        val metadatas = jsonLoader.getBooksMetadataDto()
            ?: return Resource.Error(ERROR_LOAD_METADATAS)

        bookMetadataDao.insertAllMetadatas(metadatas.toEntities())
        return Resource.Success()
    }

    override suspend fun getBook(bookId: Int): Flow<Resource<Book>> =
        flow {
            emit(Resource.Loading(true))

            val bookMetadata = bookMetadataDao.getMetadata(bookId)

            val paragraphContents = jsonLoader.getBookText(bookMetadata.fileName)?.paragraphs
                ?: run {
                    emit(Resource.Error(ERROR_LOAD_BOOK))
                    return@flow
                }

            val savedInLists = bookListDao.getBookListsOfBookId(bookId)
            val bookInfo = bookMetadata.toBookInfo(savedInLists)
            val book = bookInfo.toBookWithContents(paragraphContents)

            emit(Resource.Success(book))
            emit(Resource.Loading(true))
        }

    override suspend fun getRandomBook(existingBooksIds: List<Int>): Resource<Book?> {
        val bookMetadatasCount = bookMetadataDao.getMetadatasCount()
        if(existingBooksIds.size == bookMetadatasCount) {
            return Resource.Success(null)
        }

        val randomBookMetadata = bookMetadataDao.getRandomMetadata(existingBooksIds)
        val paragraphContents = jsonLoader.getBookText(randomBookMetadata.fileName)?.paragraphs
            ?: run {
                return Resource.Error(ERROR_LOAD_BOOK)
            }
        val savedInLists = bookListDao.getBookListsOfBookId(randomBookMetadata.id)
        val randomBookInfo = randomBookMetadata.toBookInfo(savedInLists)
        val randomBook = randomBookInfo.toBookWithContents(paragraphContents)

        return Resource.Success(randomBook)
    }

    override suspend fun getCategories(): Resource<List<String>> =
        jsonLoader.getCategoriesDto()
            ?.let{ Resource.Success(it.categories) }
            ?: Resource.Error(ERROR_LOAD_CATEGORIES)

    override suspend fun getTags(): Resource<List<String>> =
        jsonLoader.getTagsDto()
            ?.let{ Resource.Success(it.tags) }
            ?: Resource.Error(ERROR_LOAD_TAGS)

}