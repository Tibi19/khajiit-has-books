package com.tam.tesbooks.presentation.screen.bookmarks

import com.tam.tesbooks.domain.model.bookmark.Bookmark

sealed class BookmarksEvent {
    data class OnDeleteBookmark(val bookmark: Bookmark): BookmarksEvent()
    object OnLoadMoreBookmarks: BookmarksEvent()
}