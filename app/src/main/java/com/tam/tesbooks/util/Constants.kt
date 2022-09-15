package com.tam.tesbooks.util

const val DATABASE_BOOK_INFO = "database_book_info"
const val TABLE_BOOK_LIST = "table_book_list"
const val TABLE_BOOK_SAVE = "table_book_save"
const val TABLE_BOOKMARK = "table_bookmark"
const val TABLE_BOOK_METADATA = "table_book_metadata"

const val INSTANCE_IO_DISPATCHER = "io_dispatcher"
const val INSTANCE_DEFAULT_DISPATCHER = "default_dispatcher"
const val INSTANCE_APPLICATION_SCOPE = "application_scope"

const val PATH_BOOKS_METADATA = "books_metadata.json"
const val PATH_BOOKS_CATEGORIES = "books_categories.json"
const val PATH_BOOKS_TAGS = "books_tags.json"
const val PATH_BOOK_TEXTS_FOLDER = "book_texts/"

const val SIZE_BOOK_METADATAS = 5395
const val SIZE_DEFAULT_BOOK_LISTS = 3
const val LIMIT_ROOM_QUERY_DEFAULT = 20

const val DEFAULT_BOOK_LIST_READ = "Read"
const val DEFAULT_BOOK_LIST_READ_LATER = "Read Later"
const val DEFAULT_BOOK_LIST_FAVORITE = "Favorite"

const val ERROR_NO_DEFAULT_LISTS = "Default book lists have not been initialized"
const val ERROR_LOAD_METADATAS = "Could not load books information from file"
const val ERROR_LOAD_CATEGORIES = "Could not load categories from file"
const val ERROR_LOAD_TAGS = "Could not load tags from file"
const val FALLBACK_ERROR_BOOK_INFOS = "Could not load information about books"