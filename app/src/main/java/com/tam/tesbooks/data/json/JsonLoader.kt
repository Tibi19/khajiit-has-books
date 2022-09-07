package com.tam.tesbooks.data.json

import com.tam.tesbooks.data.json.dto.*

interface JsonLoader {

    suspend fun getBooksMetadataDto(): BooksMetadataDto

    suspend fun getBookSizesDto(): BookSizesDto

    suspend fun getBookTextDto(): BookTextDto

    suspend fun getCategoriesDto(): CategoriesDto

    suspend fun getTagsDto(): TagsDto

}