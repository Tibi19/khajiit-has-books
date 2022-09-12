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

    @Query("SELECT * FROM table_book_metadata WHERE id IN (:bookIds)")
    suspend fun getMetadatasWithIds(bookIds: List<Int>): List<BookMetadataEntity>

    @Query("SELECT COUNT(id) FROM table_book_metadata")
    suspend fun getMetadatasCount(): Int

    @Query("SELECT MAX(id) FROM table_book_metadata")
    suspend fun getMaxMetadataId(): Int

    @Query(
        """
            SELECT * FROM table_book_metadata 
            ORDER BY RANDOM() 
            LIMIT :limit
        """
    )
    suspend fun getRandomMetadatas(limit: Int = LIMIT_ROOM_QUERY_DEFAULT): List<BookMetadataEntity>

    @Query(
        """
            SELECT * FROM table_book_metadata 
            WHERE id NOT IN (:exceptIds) 
            ORDER BY RANDOM() 
            LIMIT :limit
        """
    )
    suspend fun getRandomMetadatasExcept(exceptIds: List<Int>, limit: Int = LIMIT_ROOM_QUERY_DEFAULT): List<BookMetadataEntity>

    @Query(
        """
            SELECT * FROM table_book_metadata
            ORDER BY 
                CASE WHEN :isReversed = 0 THEN textSize END DESC,
                CASE WHEN :isReversed = 1 THEN textSize END ASC
            LIMIT :limit
        """
    )
    suspend fun getMetadatasBySize(isReversed: Boolean = false, limit: Int = LIMIT_ROOM_QUERY_DEFAULT): List<BookMetadataEntity>

    @Query(
        """
            SELECT * FROM table_book_metadata 
            WHERE textSize < :textSize 
            ORDER BY textSize DESC 
            LIMIT :limit
        """
    )
    suspend fun getMetadatasBeforeSize(textSize: Int, limit: Int = LIMIT_ROOM_QUERY_DEFAULT): List<BookMetadataEntity>

    @Query(
        """
            SELECT * FROM table_book_metadata 
            WHERE textSize > :textSize 
            ORDER BY textSize ASC 
            LIMIT :limit
        """
    )
    suspend fun getMetadatasAfterSize(textSize: Int, limit: Int = LIMIT_ROOM_QUERY_DEFAULT): List<BookMetadataEntity>

//    @RawQuery(observedEntities = [BookMetadataEntity::class])
//    suspend fun getWithQuery(query: SupportSQLiteQuery): List<BookMetadataEntity>

}