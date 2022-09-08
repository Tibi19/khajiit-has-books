package com.tam.tesbooks.data.repository

import com.tam.tesbooks.data.json.JsonLoader
import com.tam.tesbooks.data.json.dto.BookTextDto
import com.tam.tesbooks.data.json.dto.BooksMetadataDto
import com.tam.tesbooks.data.room.database.BookInfoDatabase
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.metadata.BooksMetadata
import com.tam.tesbooks.util.printTest
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.system.measureTimeMillis

@Singleton
class RepositoryTest @Inject constructor(
    private val database: BookInfoDatabase,
    private val jsonLoader: JsonLoader
) {

    private val bookListDao = database.bookListDao
    private val bookmarkDao = database.bookmarkDao
    private val bookSaveDao = database.bookSaveDao

    fun testList(): BookList {
        return BookList(15, "Test List", true)
    }

    suspend fun testTags(): List<String> = jsonLoader.getTagsDto()?.tags ?: listOf()

    suspend fun testBooksMetadata(): BooksMetadataDto? {
        return jsonLoader.getBooksMetadataDto()
    }

    suspend fun testBookText(fileName: String): BookTextDto? {
        return jsonLoader.getBookText(fileName)
    }

    suspend fun testTagsAndCategories() {
//        val time = measureTimeMillis {
//            jsonLoader.getTagsDto()
//            jsonLoader.getCategoriesDto()
//            jsonLoader.getBooksMetadataDto()
//            jsonLoader.getBookSizesDto()
//        }
//        printTest("Loaded all in $time")
    }

}