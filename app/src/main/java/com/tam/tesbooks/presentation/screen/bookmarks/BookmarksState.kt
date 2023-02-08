package com.tam.tesbooks.presentation.screen.bookmarks

import com.tam.tesbooks.domain.model.bookmark.Bookmark

data class BookmarksState(
    val bookmarks: List<Bookmark> = emptyList(),
    val isLoading: Boolean = false,
)
