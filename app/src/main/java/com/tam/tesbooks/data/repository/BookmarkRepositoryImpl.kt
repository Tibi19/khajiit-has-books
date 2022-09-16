package com.tam.tesbooks.data.repository

import com.tam.tesbooks.data.mapper.toBookmarks
import com.tam.tesbooks.data.mapper.toEntity
import com.tam.tesbooks.data.room.database.BookInfoDatabase
import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    database: BookInfoDatabase
): BookmarkRepository {

    private val bookmarkDao = database.bookmarkDao

    override suspend fun getBookmarks(lastLoadedBookmark: Bookmark?): Flow<Resource<List<Bookmark>>> =
        flow {
            emit(Resource.Loading(true))

            val bookmarkEntities = lastLoadedBookmark
                ?.let { bookmarkDao.getBookmarks(before = it.dateTimeAdded) }
                ?: bookmarkDao.getBookmarks()
            val bookmarks = bookmarkEntities.toBookmarks()

            emit(Resource.Success(bookmarks))
            emit(Resource.Loading(true))
        }

    override suspend fun addBookmark(bookmark: Bookmark) =
        bookmarkDao.insertBookmark(bookmark.toEntity())

    override suspend fun removeBookmark(bookmark: Bookmark) =
        bookmarkDao.deleteBookmark(bookmark.id)

}