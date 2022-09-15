package com.tam.tesbooks.data.repository

import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.util.Resource

interface JsonRepository {

    suspend fun saveMetadatasFromJson(): Resource<Unit>

    suspend fun getBook(bookInfo: BookInfo): Resource<Book>

    suspend fun getCategories(): Resource<List<String>>

    suspend fun getTags(): Resource<List<String>>

}