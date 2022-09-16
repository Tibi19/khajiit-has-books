package com.tam.tesbooks.data.repository

import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.util.Resource
import kotlinx.coroutines.flow.Flow

interface JsonRepository {

    suspend fun saveMetadatasFromJson(): Resource<Unit>

    suspend fun getBook(bookInfo: BookInfo): Flow<Resource<Book>>

    suspend fun getCategories(): Resource<List<String>>

    suspend fun getTags(): Resource<List<String>>

}