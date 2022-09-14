package com.tam.tesbooks.data.repository

import com.tam.tesbooks.data.json.JsonLoader
import com.tam.tesbooks.data.mapper.toBookInfosWithLists
import com.tam.tesbooks.data.mapper.toEntities
import com.tam.tesbooks.data.room.database.BookInfoDatabase
import com.tam.tesbooks.data.room.entity.BookListEntity
import com.tam.tesbooks.data.room.entity.BookMetadataEntity
import com.tam.tesbooks.data.room.query.DynamicMetadataQuery
import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.domain.model.listing_modifier.*
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val database: BookInfoDatabase,
    private val jsonLoader: JsonLoader
): Repository {

    private val bookListDao = database.bookListDao
    private val bookmarkDao = database.bookmarkDao
    private val bookSaveDao = database.bookSaveDao
    private val bookMetadataDao = database.bookMetadataDao

    override suspend fun initializeDatabase() {
        saveMetadatas()
        saveDefaultBookLists()
    }

    private suspend fun saveMetadatas() {
        val metadatasCount = bookMetadataDao.getMetadatasCount()
        if(metadatasCount >= SIZE_BOOK_METADATAS) return

        val metadatas = jsonLoader.getBooksMetadataDto()?.toEntities() ?: return
        bookMetadataDao.insertAllMetadatas(metadatas)
    }

    private suspend fun saveDefaultBookLists() {
        val defaultBookListsCount = bookListDao.getDefaultBookListsCount()
        if(defaultBookListsCount >= SIZE_DEFAULT_BOOK_LISTS) return

        val defaultBookLists = listOf(
            BookListEntity(0, DEFAULT_BOOK_LIST_READ, true),
            BookListEntity(0, DEFAULT_BOOK_LIST_READ_LATER, true),
            BookListEntity(0, DEFAULT_BOOK_LIST_FAVORITE, true)
        )
        defaultBookLists.forEach { bookListDao.insertBookList(it) }
    }

    override suspend fun getBookInfos(
        libraryOrder: LibraryOrder,
        libraryFilter: LibraryFilter,
        alreadyLoadedInfos: List<BookInfo>,
        searchQuery: String
    ): List<BookInfo>? {
        val dynamicMetadataQuery = DynamicMetadataQuery
            .build {
                decideCategories(libraryOrder.orderBy)
                order(libraryOrder)
                search(searchQuery)
                filter(libraryFilter)
                decideExclusion(libraryOrder.orderBy, alreadyLoadedInfos)
            }
        val bookMetadatas = getBookMetadatas(dynamicMetadataQuery) ?: return null
        val bookInfos = bookMetadatas
            .toBookInfosWithLists {
                bookListDao.getBookListsOfBookId(id)
            }
        return bookInfos
    }

    private suspend fun getBookMetadatas(dynamicMetadataQuery: DynamicMetadataQuery): List<BookMetadataEntity>? =
        try {
            bookMetadataDao.getMetadatasWithQuery(dynamicMetadataQuery.rawQuery)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

    private suspend fun DynamicMetadataQuery.Builder.decideCategories(order: Order?) {
        if(order != Order.CATEGORY) return
        withCategories(getCategories())
    }

    private fun DynamicMetadataQuery.Builder.decideExclusion(order: Order?, alreadyLoadedInfos: List<BookInfo>) {
        if(alreadyLoadedInfos.isEmpty()) return
        val lastMetadata = alreadyLoadedInfos.last().metadata
        when (order) {
            Order.SIZE -> following(lastMetadata.textSize)
            Order.TITLE -> following(lastMetadata.title)
            else -> except(alreadyLoadedInfos.map { it.bookId })
        }
    }

    private suspend fun DynamicMetadataQuery.Builder.decideExclusion(sort: Sort, bookList: BookList, alreadyLoadedInfos: List<BookInfo>) {
        if(alreadyLoadedInfos.isEmpty()) return
        val lastMetadata = alreadyLoadedInfos.last().metadata
        when(sort) {
            Sort.SIZE -> following(lastMetadata.textSize)
            Sort.TITLE -> following(lastMetadata.title)
            Sort.DATE_ADDED -> {
                val whenLastBookWasSaved = bookSaveDao.getDateTimeSaved(lastMetadata.id, bookList.id)
                following(whenLastBookWasSaved)
            }
        }
    }

    override suspend fun getBookInfosFromList(
        bookList: BookList,
        bookListSort: BookListSort,
        alreadyLoadedInfos: List<BookInfo>
    ): List<BookInfo>? {
        TODO("Not yet implemented")
    }

    override suspend fun getBook(bookInfo: BookInfo): Book {
        TODO("Not yet implemented")
    }

    override suspend fun getBookLists(): List<BookList> {
        TODO("Not yet implemented")
    }

    override suspend fun addBookList(bookList: BookList) {
        TODO("Not yet implemented")
    }

    override suspend fun editBookList(bookList: BookList) {
        TODO("Not yet implemented")
    }

    override suspend fun removeBookList(bookList: BookList) {
        TODO("Not yet implemented")
    }

    override suspend fun addBookToList(bookInfo: BookInfo, bookList: BookList) {
        TODO("Not yet implemented")
    }

    override suspend fun removeBookFromList(bookInfo: BookInfo, bookList: BookList) {
        TODO("Not yet implemented")
    }

    override suspend fun getBookmarks(lastLoadedBookmark: Bookmark?): List<Bookmark> {
        TODO("Not yet implemented")
    }

    override suspend fun addBookmark(bookmark: Bookmark) {
        TODO("Not yet implemented")
    }

    override suspend fun removeBookmark(bookmark: Bookmark) {
        TODO("Not yet implemented")
    }

    override suspend fun getCategories(): List<String> = jsonLoader.getCategoriesDto()?.categories ?: emptyList()

    override suspend fun getTags(): List<String> = jsonLoader.getTagsDto()?.tags ?: emptyList()
}
