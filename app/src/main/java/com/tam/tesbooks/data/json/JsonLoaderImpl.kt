package com.tam.tesbooks.data.json

import android.app.Application
import com.squareup.moshi.Moshi
import com.tam.tesbooks.data.json.dto.*
import com.tam.tesbooks.util.*
import kotlinx.coroutines.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import kotlin.system.measureTimeMillis

@Singleton
class JsonLoaderImpl @Inject constructor(
    private val moshi: Moshi,
    private val application: Application,
    @Named(INSTANCE_IO_DISPATCHER) private val ioDispatcher: CoroutineDispatcher,
    @Named(INSTANCE_APPLICATION_SCOPE) private val applicationScope: CoroutineScope
): JsonLoader {

    private val categoriesAsync: Deferred<CategoriesDto?> =
        applicationScope.async(ioDispatcher) {
            getDtoFromJsonFile(CategoriesDto::class.java, PATH_BOOKS_CATEGORIES)
        }

    private val tagsAsync: Deferred<TagsDto?> =
        applicationScope.async(ioDispatcher) {
            getDtoFromJsonFile(TagsDto::class.java, PATH_BOOKS_TAGS)
        }

    private suspend fun <T> getDtoFromJsonFile(dtoClass: Class<T>, pathToJson: String): T? =
        try {
            // we use runInterruptible so that blocking functions 'open' and 'fromJson' can be cancelled
            runInterruptible {
                val jsonFile = application.assets.open(pathToJson).bufferedReader()
                val json = jsonFile.use { it.readText() }
                val jsonAdapter = moshi.adapter(dtoClass)
                jsonAdapter.fromJson(json)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    override suspend fun getBooksMetadataDto(): BooksMetadataDto? = getBooksMetadataAsync().await()

    private suspend fun getBooksMetadataAsync(): Deferred<BooksMetadataDto?> =
        applicationScope.async(ioDispatcher) {
            getDtoFromJsonFile(BooksMetadataDto::class.java, PATH_BOOKS_METADATA)
        }

    override suspend fun getCategoriesDto(): CategoriesDto? = categoriesAsync.await()

    override suspend fun getTagsDto(): TagsDto? = tagsAsync.await()

    override suspend fun getBookText(fileName: String): BookTextDto? = getBookTextAsync(fileName).await()

    private suspend fun getBookTextAsync(fileName: String): Deferred<BookTextDto?> =
        applicationScope.async(ioDispatcher) {
            val pathToJson = "$PATH_BOOK_TEXTS_FOLDER$fileName"
            getDtoFromJsonFile(BookTextDto::class.java, pathToJson)
        }
}