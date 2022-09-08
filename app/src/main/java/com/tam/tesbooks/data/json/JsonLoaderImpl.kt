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

//    private val booksMetadataAsync: Deferred<BooksMetadataDto?> =
//        applicationScope.async(ioDispatcher) {
////            getDtoFromJsonFile(BooksMetadataDto::class.java, PATH_BOOKS_METADATA)
//            getDtoWithTimeTest(BooksMetadataDto::class.java, PATH_BOOKS_METADATA, "Loaded books metadata")
//        }

    private val categoriesAsync: Deferred<CategoriesDto?> =
        applicationScope.async(ioDispatcher) {
            getDtoWithTimeTest(CategoriesDto::class.java, PATH_BOOKS_CATEGORIES, "Loaded categories")
        }

    private val tagsAsync: Deferred<TagsDto?> =
        applicationScope.async(ioDispatcher) {
            getDtoWithTimeTest(TagsDto::class.java, PATH_BOOKS_TAGS, "Loaded tags")
        }

    private suspend fun getBooksMetadataAsync(): Deferred<BooksMetadataDto?> =
        applicationScope.async(ioDispatcher) {
            getDtoWithTimeTest(BooksMetadataDto::class.java, PATH_BOOKS_METADATA, "Loaded books metadata")
        }

    private suspend fun getCategoriesAsync(): Deferred<CategoriesDto?> =
        applicationScope.async(ioDispatcher) {
            getDtoWithTimeTest(CategoriesDto::class.java, PATH_BOOKS_CATEGORIES, "Loaded categories")
        }

    private suspend fun getTagsAsync(): Deferred<TagsDto?> =
        applicationScope.async(ioDispatcher) {
            getDtoWithTimeTest(TagsDto::class.java, PATH_BOOKS_TAGS, "Loaded tags")
        }

    private suspend fun <T> getDtoWithTimeTest(dtoClass: Class<T>, pathToJson: String, message: String): T? {
        var tDto: T?
        val time = measureTimeMillis {
            tDto = getDtoFromJsonFile(dtoClass, pathToJson)
        }
        printTest("$message in $time")
        return tDto
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

    override suspend fun getCategoriesDto(): CategoriesDto? = getCategoriesAsync().await()

    override suspend fun getTagsDto(): TagsDto? = getTagsAsync().await()

    override suspend fun getBookText(fileName: String): BookTextDto? = getBookTextAsync(fileName).await()

    private suspend fun getBookTextAsync(fileName: String): Deferred<BookTextDto?> =
        applicationScope.async(ioDispatcher) {
            val pathToJson = "$PATH_BOOK_TEXTS_FOLDER$fileName"
//            getDtoFromJsonFile(BookTextDto::class.java, pathToJson)
            getDtoWithTimeTest(BookTextDto::class.java, pathToJson, "Loaded $fileName book text")
        }
}