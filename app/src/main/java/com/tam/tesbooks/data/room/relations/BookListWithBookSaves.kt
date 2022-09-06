package com.tam.tesbooks.data.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.tam.tesbooks.data.room.entity.BookListEntity
import com.tam.tesbooks.data.room.entity.BookSaveEntity

data class BookListWithBookSaves(
    @Embedded val bookListEntity: BookListEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "bookListId",
    )
    val bookSaves: List<BookSaveEntity>
)