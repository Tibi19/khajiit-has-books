package com.tam.tesbooks.data.room.query

import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.tam.tesbooks.domain.model.listing_modifier.*
import com.tam.tesbooks.util.LIMIT_ROOM_QUERY_DEFAULT
import com.tam.tesbooks.util.TABLE_BOOK_METADATA
import com.tam.tesbooks.util.TABLE_BOOK_SAVE
import java.time.LocalDateTime
import java.time.ZoneId

class DynamicMetadataQuery(val query: String) {

    private constructor(builder: Builder): this(builder.query)

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    val rawQuery: SupportSQLiteQuery
        get() = SimpleSQLiteQuery(query)

    class Builder {

        companion object {
            const val TABLE = TABLE_BOOK_METADATA
            const val TABLE_SAVES = TABLE_BOOK_SAVE
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
        private lateinit var sort: Sort

        private lateinit var column: String
        private lateinit var comparator: String
        private lateinit var categoriesOrder: List<String>

        fun build() = DynamicMetadataQuery(this)

        fun withCategories(categoriesOrder: List<String>) {
            this.categoriesOrder = categoriesOrder
        }

        fun order(libraryOrder: LibraryOrder) {
            isReversed = libraryOrder.isReversed
            order = libraryOrder.orderBy
            when(order) {
                Order.TITLE -> orderByTitle()
                Order.CATEGORY -> orderByCategory()
                Order.AUTHOR_NAME -> orderByAuthorName()
                Order.SIZE -> orderBySize()
                else -> orderRandomly()
            }
        }

        private fun setColumn(column: String) {
            if(this::column.isInitialized && this.column.isNotEmpty()) return
            this.column = column
        }

        /**
         * Can be used in conjunction with ordering or sorting by size.
         * Otherwise use except(exceptIds) to omit elements that are not needed.
         */
        fun following(value: Int) {
            addNewWhereClause()
            where += "$column $comparator $value"
        }

        /**
         * Can be used in conjunction with ordering or sorting by title.
         * Otherwise use except(exceptIds) to omit elements that are not needed.
         */
        fun following(value: String) {
            addNewWhereClause()
            where += "$column $comparator \"$value\""
        }

        /**
         * Can be used in conjunction with sorting by date added.
         * Otherwise use except(exceptIds) to omit elements that are not needed.
         */
        fun following(date: LocalDateTime) {
            addNewWhereClause()
            val timestamp = date.atZone(ZoneId.systemDefault()).toEpochSecond()
            where += "$column $comparator $timestamp"
        }

        private fun addNewWhereClause() {
            where += if(where.contains("WHERE")) " AND " else "WHERE "
        }

        private fun orderByTitle() {
            setColumn("title")
            comparator = if(isReversed) "<" else ">"
            orderBy = "ORDER BY $column"
            orderAs = if(isReversed) "DESC" else "ASC"
        }

        private fun orderByCategory() {
            setColumn("category")

            orderAs = if(isReversed) "DESC" else "ASC"
            orderBy = "ORDER BY CASE $column"
            categoriesOrder.forEachIndexed { i, category ->
                orderBy += " WHEN \'$category\' THEN $i"
            }
            orderBy += " END"
        }

        private fun orderByAuthorName() {
            setColumn("author")
            comparator = if(isReversed) "<" else ">"
            orderBy = "ORDER BY $column"
            orderAs = if(isReversed) "DESC" else "ASC"
        }

        private fun orderBySize() {
            setColumn("textSize")

            // Default ordering for size should be from biggest to lowest
            comparator = if(isReversed) ">" else "<"
            orderAs = if(isReversed) "ASC" else "DESC"

            orderBy = "ORDER BY $column"
        }

        private fun orderRandomly() {
            orderBy = "ORDER BY RANDOM()"
        }

        fun except(exceptIds: List<Int>) {
            addNewWhereClause()
            val exceptIdsString = exceptIds.joinToString(", ")
            where += "id NOT IN ($exceptIdsString)"
        }

        fun search(searchQuery: String) {
            if(searchQuery.isEmpty()) return

            val likeQuery = "LIKE '%' || LOWER(\"$searchQuery\") || '%'"
            addNewWhereClause()
            where += "(LOWER(title) $likeQuery OR "
            where += "LOWER(author) $likeQuery OR "
            where += "LOWER(description) $likeQuery)"
        }

        fun filter(libraryFilter: LibraryFilter) {
            if(libraryFilter.tagFilters.isNotEmpty()) {
                filterTags(libraryFilter.tagFilters)
            }
            if(libraryFilter.categoryFilters.isNotEmpty()) {
                filterCategories(libraryFilter.categoryFilters)
            }
            if(libraryFilter.isExcludingAnonymous) {
                filterAnonymous()
            }
        }

        private fun filterTags(tags: List<String>) {
            addNewWhereClause()
            where += "("
            tags.forEachIndexed { i, tag ->
                where += "LOWER(tags) LIKE '%' || LOWER('$tag') || '%'"
                if (i < tags.size - 1) {
                    where += " OR "
                }
            }
            where += ")"
        }

        private fun filterCategories(categories: List<String>) {
            addNewWhereClause()
            where += "("
            categories.forEachIndexed { i, category ->
                where += "LOWER(tags) LIKE '%' || LOWER('$category') || '%'"
                if (i < categories.size - 1) {
                    where += " OR "
                }
            }
            where += ")"
        }

        private fun filterAnonymous() {
            addNewWhereClause()
            where += "author != 'Anonymous'"
        }

        fun fromIds(ids: List<Int>) {
            addNewWhereClause()
            val idsString = ids.joinToString(", ")
            where += "id IN ($idsString)"
        }

        fun fromBookList(bookListId: Int) {
            from = "FROM $TABLE_SAVES INNER JOIN $TABLE ON $TABLE_SAVES.bookId = $TABLE.id"
            addNewWhereClause()
            where += "bookListId = $bookListId"
        }

        fun sortList(bookListSort: BookListSort) {
            isReversed = bookListSort.isReversed
            sort = bookListSort.sortBy
            when(sort) {
                Sort.DATE_ADDED -> orderByDateAdded()
                Sort.SIZE -> orderBySize()
                Sort.TITLE -> orderByTitle()
            }
        }

        private fun orderByDateAdded() {
            setColumn("dateTimeSaved")
            orderBy = "ORDER BY $column"
            // Default ordering for date added should be from recent to old
            comparator = if(isReversed) ">" else "<"
            orderAs = if(isReversed) "ASC" else "DESC"
        }

        fun limit(limitElements: Int?) {
            if(limitElements == null || limitElements <= 0) {
                limit = ""
                return
            }
            limit = "LIMIT $limitElements"
        }

    }

}