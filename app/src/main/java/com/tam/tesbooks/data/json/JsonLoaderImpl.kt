package com.tam.tesbooks.data.json

import com.squareup.moshi.Moshi
import com.tam.tesbooks.data.json.dto.*
import com.tam.tesbooks.util.INSTANCE_APPLICATION_SCOPE
import com.tam.tesbooks.util.INSTANCE_DEFAULT_DISPATCHER
import com.tam.tesbooks.util.INSTANCE_IO_DISPATCHER
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class JsonLoaderImpl @Inject constructor(
    private val moshi: Moshi,
    @Named(INSTANCE_IO_DISPATCHER)
    private val ioDispatcher: CoroutineDispatcher,
    @Named(INSTANCE_DEFAULT_DISPATCHER)
    private val defaultDispatcher: CoroutineDispatcher,
    @Named(INSTANCE_APPLICATION_SCOPE)
    private val applicationScope: CoroutineScope
): JsonLoader {

    override suspend fun getBooksMetadataDto(): BooksMetadataDto {
        TODO("Not yet implemented")
    }

    override suspend fun getBookSizesDto(): BookSizesDto {
        TODO("Not yet implemented")
    }

    override suspend fun getBookTextDto(): BookTextDto {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoriesDto(): CategoriesDto {
        TODO("Not yet implemented")
    }

    override suspend fun getTagsDto(): TagsDto {
        TODO("Not yet implemented")
    }
}