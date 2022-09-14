package com.tam.tesbooks.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tam.tesbooks.data.room.entity.BookmarkEntity
import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.util.LIMIT_ROOM_QUERY_DEFAULT
import java.time.LocalDateTime

@Dao
interface BookmarkDao {

    @Insert
    suspend fun insertBookmark(bookmarkEntity: BookmarkEntity)

    @Query("DELETE FROM table_bookmark WHERE id = :bookmarkId")
    suspend fun deleteBookmark(bookmarkId: Int)

    @Query("SELECT * FROM table_bookmark WHERE dateTimeAdded < :before ORDER BY dateTimeAdded DESC LIMIT :limitElements")
    suspend fun getBookmarks(
        before: LocalDateTime = LocalDateTime.now(),
        limitElements: Int = LIMIT_ROOM_QUERY_DEFAULT
    ): List<BookmarkEntity>

}