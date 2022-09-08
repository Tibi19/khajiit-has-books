package com.tam.tesbooks.data.json

import com.tam.tesbooks.data.json.dto.*

interface JsonLoader {

    suspend fun getBooksMetadataDto(): BooksMetadataDto?

    suspend fun getCategoriesDto(): CategoriesDto?

    suspend fun getTagsDto(): TagsDto?

    suspend fun getBookText(fileName: String): BookTextDto?

}