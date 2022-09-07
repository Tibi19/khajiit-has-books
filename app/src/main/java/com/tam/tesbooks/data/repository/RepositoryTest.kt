package com.tam.tesbooks.data.repository

import com.tam.tesbooks.data.json.JsonLoader
import com.tam.tesbooks.data.room.database.BookInfoDatabase
import com.tam.tesbooks.domain.model.book_list.BookList
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

    fun testList(): BookList {
        return BookList(15, "Test List", true)
    }

}