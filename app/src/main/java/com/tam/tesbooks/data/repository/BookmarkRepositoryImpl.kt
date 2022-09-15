package com.tam.tesbooks.data.repository

import com.tam.tesbooks.data.room.database.BookInfoDatabase
import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(
    database: BookInfoDatabase
): BookmarkRepository {

    private val bookmarkDao = database.bookmarkDao

    override suspend fun getBookmarks(lastLoadedBookmark: Bookmark?): Flow<Resource<List<Bookmark>>> {
        TODO("Not yet implemented")
    }

    override suspend fun addBookmark(bookmark: Bookmark) {
        TODO("Not yet implemented")
    }

    override suspend fun removeBookmark(bookmark: Bookmark) {
        TODO("Not yet implemented")
    }
}