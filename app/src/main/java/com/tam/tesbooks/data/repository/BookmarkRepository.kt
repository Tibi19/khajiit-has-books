package com.tam.tesbooks.data.repository

import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.util.Resource
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun getBookmarks(lastLoadedBookmark: Bookmark? = null): Flow<Resource<List<Bookmark>>>

    suspend fun getBookmarksOfBookId(bookId: Int): Flow<Resource<List<Bookmark>>>

    suspend fun addBookmark(bookmark: Bookmark)

    suspend fun removeBookmark(bookmark: Bookmark)

}