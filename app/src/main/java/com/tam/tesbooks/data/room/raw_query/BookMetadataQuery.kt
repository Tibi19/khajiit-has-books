package com.tam.tesbooks.data.room.raw_query

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.tam.tesbooks.domain.model.listing_modifier.LibraryFilter
import com.tam.tesbooks.domain.model.listing_modifier.LibraryOrder
import com.tam.tesbooks.domain.model.listing_modifier.Order
import com.tam.tesbooks.util.LIMIT_ROOM_QUERY_DEFAULT

class BookMetadataQuery(val query: String) {

    private constructor(builder: Builder): this(builder.query)

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    val rawQuery: SupportSQLiteQuery
        get() = SimpleSQLiteQuery(query)

    class Builder {

        companion object {
            const val TABLE = "table_book_metadata"
        }

        private var select: String = "SELECT *"
        private var from: String = "FROM $TABLE"
        private var where: String = ""
        private var orderBy: String = ""
        private var orderAs: String = ""
        private var limit: String = "LIMIT $LIMIT_ROOM_QUERY_DEFAULT"

        val query: String
            get() = "$select $from $where $orderBy $orderAs $limit".trim()

        private var order: Order? = null
        private var isReversed: Boolean = false

        private lateinit var column: String
        private lateinit var comparator: String
        private lateinit var categoriesOrder: List<String>

        fun build() = BookMetadataQuery(this)

        fun withCategories(categoriesOrder: List<String>) {
            this.categoriesOrder = categoriesOrder
        }

        fun order(libraryOrder: LibraryOrder) {
            isReversed = libraryOrder.isReversed
            order = libraryOrder.orderBy
            when(order) {
                Order.TITLE -> orderByTitle()
                Order.CATEGORY -> orderByCategory()
                Order.AUTHOR_NAME -> orderByAuthor()
                Order.AUTHOR_COUNT -> orderByAuthorCount()
                Order.SIZE -> orderBySize()
                else -> orderRandomly()
            }
        }

        private fun setColumn(column: String) {
            if(this.column.isNotEmpty()) return
            this.column = column
        }

        fun after(value: Int) = after(value.toString())

        fun after(value: String) {
            addNewWhereClause()
            where += "$column $comparator $value"
        }

        private fun addNewWhereClause() {
            where += if(where.contains("WHERE")) " AND " else "WHERE "
        }

        private fun orderByTitle() {
            TODO("Not yet implemented")
        }

        private fun orderByCategory() {
            TODO("Not yet implemented")
        }

        private fun orderByAuthor() {
            TODO("Not yet implemented")
        }

        private fun orderByAuthorCount() {
            TODO("Not yet implemented")
        }

        private fun orderBySize() {
            setColumn("textSize")
            comparator = if(isReversed) ">" else "<"
            orderBy = "ORDER BY $column"
            orderAs = if (isReversed) "ASC" else "DESC"
        }

        private fun orderRandomly() {
            TODO("Not yet implemented")
        }

        fun except(exceptIds: List<Int>) {
            addNewWhereClause()
            val exceptIdsString = exceptIds.joinToString(", ")
            where += "id NOT IN ($exceptIdsString)"
        }

        fun filter(libraryFilter: LibraryFilter) {

        }

    }

}