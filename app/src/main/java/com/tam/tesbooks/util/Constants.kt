package com.tam.tesbooks.util

// Database naming
const val DATABASE_BOOK_INFO = "database_book_info"
const val TABLE_BOOK_LIST = "table_book_list"
const val TABLE_BOOK_SAVE = "table_book_save"
const val TABLE_BOOKMARK = "table_bookmark"
const val TABLE_BOOK_METADATA = "table_book_metadata"

// DI Naming
const val INSTANCE_IO_DISPATCHER = "io_dispatcher"
const val INSTANCE_DEFAULT_DISPATCHER = "default_dispatcher"
const val INSTANCE_APPLICATION_SCOPE = "application_scope"

// file paths
const val PATH_BOOKS_METADATA = "books_metadata.json"
const val PATH_BOOKS_CATEGORIES = "books_categories.json"
const val PATH_BOOKS_TAGS = "books_tags.json"
const val PATH_BOOK_TEXTS_FOLDER = "book_texts/"

// Sizes
const val SIZE_BOOK_METADATAS = 5395
const val SIZE_DEFAULT_BOOK_LISTS = 3
const val LIMIT_ROOM_QUERY_DEFAULT = 20
const val TIMES_TO_TRY_BRINGING_INTO_VIEW_NAME_FIELD = 3

// Default lists naming
const val DEFAULT_BOOK_LIST_VIEWED = "Viewed"
const val DEFAULT_BOOK_LIST_READ_LATER = "Read Later"
const val DEFAULT_BOOK_LIST_FAVORITE = "Favorite"

// Error messages
const val ERROR_NO_DEFAULT_LISTS = "Default book lists have not been initialized"
const val ERROR_LOAD_METADATAS = "Could not load books information from file"
const val ERROR_LOAD_CATEGORIES = "Could not load categories from file"
const val ERROR_LOAD_TAGS = "Could not load tags from file"
const val ERROR_LOAD_BOOK = "Could not load book from file"
const val ERROR_LOAD_NEXT_BOOK_BECAUSE = "Next book could not be loaded: "
const val FALLBACK_ERROR_LOAD_BOOK_INFOS = "Could not load information about books"
const val FALLBACK_ERROR_LOAD_BOOK_LISTS = "Could not load book lists"
const val FALLBACK_ERROR_UPDATE_BOOK_INFO = "Could not update book details"
const val FALLBACK_ERROR_LOAD_BOOK = "Could not load book"
const val FALLBACK_ERROR_LOAD_TAGS = "Could not load tags"
const val FALLBACK_ERROR_LOAD_CATEGORIES = "Could not load categories"
const val FALLBACK_ERROR_LOAD_BOOKMARKS = "Could not load bookmarks"
const val FALLBACK_ERROR_UNKNOWN = "Unknown error"
const val FALLBACK_URL_NO_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/0/0a/No-image-available.png"
const val FALLBACK_ERROR_COUNT_BOOK_SAVES = "Could not count book saves in book list"
const val FALLBACK_ERROR_DATABASE = "Could not load database"

// Paragraph content tags
const val TAG_IMAGE = "<image="
const val TAG_TITLE = "<title>"
const val TAG_ITALIC = "<italic>"
const val TAG_BOLD = "<bold>"
const val TAG_HEADER = "<header>"
const val TAG_QUOTE = "<quote>"
const val TAG_CENTER = "<center>"

// Timing
const val TIME_WAIT_AFTER_SEARCH_INPUT = 300L
const val TIME_WAIT_TO_RETRY_SHOWING_KEYBOARD_FOR_NAME_FIELD = 50L
const val TIME_WAIT_FOR_NEW_NAME_UPDATE = 150L
const val TIME_MINIMUM_FOR_LOADING_MORE_ELEMENTS = 800L
const val TIME_WAIT_FOR_UI_ON_FIRST_LOAD = 350L

// Content Description
const val CONTENT_DRAWER = "Menu Drawer"
const val CONTENT_LIBRARY = "Home Library"
const val CONTENT_SETTINGS = "Settings"
const val CONTENT_ORDER_BOOKS = "Books Ordering Options"
const val CONTENT_FILTER_BOOKS = "Books Filtering Options"
const val CONTENT_SORT_LIST = "Sort Book List Options"
const val CONTENT_SEARCH = "Search for a book"
const val CONTENT_CLEAR_TAG_FILTER = "Clear tag filter"
const val CONTENT_GO_BACK = "Go to previous screen"
const val CONTENT_CREATE_LIST = "Create new book list"
const val CONTENT_CREATE_LIST_DONE = "Submit new list with the specified name"
const val CONTENT_CREATE_LIST_CANCEL = "Cancel creating new list"
const val CONTENT_DELETE_LIST = "Delete book list"
const val CONTENT_BANNER = "Application banner"
const val CONTENT_BOOKMARKS = "Bookmarks"
const val CONTENT_BOOKMARK = "Bookmark"
const val CONTENT_FORWARD_BOOKMARKS = "Go to bookmarks screen"
const val CONTENT_DELETE_BOOKMARK = "Delete this bookmark"
const val CONTENT_LIST_VIEWED = "List of viewed books"
const val CONTENT_LIST_FAVORITE = "List of favorite books"
const val CONTENT_LIST_READ_LATER = "List of books to read later"
const val CONTENT_LIST = "Custom list"
const val CONTENT_LIST_MENU = "Menu of actions on custom list"
const val CONTENT_CANCEL_ACTION = "Cancel the current action"
const val CONTENT_CONFIRM_ACTION = "Confirm the current action"
const val CONTENT_RENAME_LIST_DONE = "Submit new name for this list"
const val CONTENT_RENAME_LIST_CANCEL = "Cancel renaming this list"

// UI Text
const val TEXT_SEARCH = "Search..."
const val TEXT_ORDER_BY_DIALOG_TITLE = "Library Order"
const val TEXT_FILTER_BY_DIALOG_TITLE = "Library Filters"
const val TEXT_CANCEL = "Cancel"
const val TEXT_SUBMIT = "Submit"
const val TEXT_TITLE = "Book Title"
const val TEXT_SIZE = "Book Size"
const val TEXT_AUTHOR = "Author Name"
const val TEXT_CATEGORY = "Book Category"
const val TEXT_FILTER_BY_TAG_SUBTITLE = "Filter By Tag"
const val TEXT_REVERSE_ORDER = "Reverse Order"
const val TEXT_EXCLUDE_ANONYMOUS = "Exclude Anonymous Author"
const val TEXT_FILTER_TAG_WARNING = "Currently filtering for the following tags: "
const val TEXT_ADD_TO_LISTS_DIALOG_TITLE = "Save to List"
const val TEXT_NEW_LIST = "New List"
const val TEXT_BOOKMARKS = "Bookmarks"
const val TEXT_BOOK_LISTS = "Book Lists"
const val TEXT_DISCLAIMER = "The Elder Scrolls series are trademarks of Bethesda Softworks LLC, a ZeniMax Media company. \n" + "All Rights Reserved."
const val TEXT_RENAME = "Rename"
const val TEXT_DELETE = "Delete"
const val TEXT_CONFIRM_LIST_DELETION_START = "Are you sure you want to delete "
const val TEXT_CONFIRMATION_DIALOG_CONFIRM = "Yes"
const val TEXT_CONFIRMATION_DIALOG_DECLINE = "No"
const val TEXT_BOOKMARK_TITLE_START = "in "
const val TEXT_NO_BOOKMARKS = "Khajiit has no bookmarks :("
const val TEXT_ADD_BOOKMARKS = "Friend can long press on paragraph to give Khajiit bookmark. Then come back here, yes?"