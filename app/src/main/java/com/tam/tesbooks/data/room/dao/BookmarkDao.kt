package com.tam.tesbooks.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tam.tesbooks.data.room.entity.BookmarkEntity

@Dao
interface BookmarkDao {

    @Insert
    suspend fun insertBookmark(bookmarkEntity: BookmarkEntity)

    @Query("DELETE FROM table_bookmark WHERE id = :bookmarkId")
    suspend fun deleteBookmark(bookmarkId: Int)

}