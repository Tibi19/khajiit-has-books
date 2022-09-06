package com.tam.tesbooks.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tam.tesbooks.data.room.entity.BookSaveEntity

@Dao
interface BookSaveDao {

    @Insert
    suspend fun insertBookSave(bookSaveEntity: BookSaveEntity)

    @Query("DELETE FROM table_bookmark WHERE id = :bookSaveId")
    suspend fun deleteBookSave(bookSaveId: Int)

}