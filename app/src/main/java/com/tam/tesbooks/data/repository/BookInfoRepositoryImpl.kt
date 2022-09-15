package com.tam.tesbooks.data.repository

import com.tam.tesbooks.data.mapper.toBookInfosWithLists
import com.tam.tesbooks.data.room.database.BookInfoDatabase
import com.tam.tesbooks.data.room.entity.BookMetadataEntity
import com.tam.tesbooks.data.room.query.DynamicMetadataQuery
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.listing_modifier.*
import com.tam.tesbooks.util.FALLBACK_ERROR_BOOK_INFOS
import com.tam.tesbooks.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookInfoRepositoryImpl @Inject constructor(
    database: BookInfoDatabase,
    private val jsonRepository: JsonRepository
): BookInfoRepository {

    private val bookMetadataDao = database.bookMetadataDao
    private val bookSaveDao = database.bookSaveDao
    private val bookListDao = database.bookListDao

    override suspend fun getBookInfos(
        libraryOrder: LibraryOrder,
        libraryFilter: LibraryFilter,
        alreadyLoadedInfos: List<BookInfo>,
        searchQuery: String
    ): Flow<Resource<List<BookInfo>>> = flow {
        emit(Resource.Loading(true))

        val dynamicMetadataQuery = DynamicMetadataQuery
            .build {
                decideCategories(libraryOrder.orderBy)
                order(libraryOrder)
                search(searchQuery)
                filter(libraryFilter)
                decideExclusion(libraryOrder.orderBy, alreadyLoadedInfos)
            }

        val metadatasResource = getBookMetadatas(dynamicMetadataQuery)
        metadatasResource.onError { message ->
            emit(Resource.Error(message!!))
            return@flow
        }

        val bookInfos = metadatasResource.data!!
            .toBookInfosWithLists {
                bookListDao.getBookListsOfBookId(id)
            }

        emit(Resource.Success(bookInfos))
        emit(Resource.Loading(false))
    }

    private suspend fun getBookMetadatas(dynamicMetadataQuery: DynamicMetadataQuery): Resource<List<BookMetadataEntity>> =
        try {
            val bookMetadatas = bookMetadataDao.getMetadatasWithQuery(dynamicMetadataQuery.rawQuery)
            Resource.Success(bookMetadatas)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: FALLBACK_ERROR_BOOK_INFOS)
        }

    private suspend fun DynamicMetadataQuery.Builder.decideCategories(order: Order?) {
        if(order != Order.CATEGORY) return
        val categoriesResource = jsonRepository.getCategories()
        categoriesResource.onError { return }
        withCategories(categoriesResource.data ?: emptyList())
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
    ): Flow<Resource<List<BookInfo>>> {
        TODO("Not yet implemented")
    }
}