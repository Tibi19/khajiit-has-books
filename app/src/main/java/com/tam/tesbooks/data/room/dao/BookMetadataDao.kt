package com.tam.tesbooks.data.room.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.tam.tesbooks.data.room.entity.BookMetadataEntity
import com.tam.tesbooks.util.LIMIT_ROOM_QUERY_DEFAULT
import javax.sql.DataSource

@Dao
interface BookMetadataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMetadatas(bookMetadataEntities: List<BookMetadataEntity>)

    @Query("SELECT * FROM table_book_metadata WHERE id = :bookId")
    suspend fun getMetadata(bookId: Int): BookMetadataEntity

    @Query("SELECT * FROM table_book_metadata")
    suspend fun getMetadatas(): List<BookMetadataEntity>

    @Query("SELECT COUNT(id) FROM table_book_metadata")
    suspend fun getMetadatasCount(): Int

    @RawQuery(observedEntities = [BookMetadataEntity::class])
    suspend fun getMetadatasWithQuery(query: SupportSQLiteQuery): List<BookMetadataEntity>

}