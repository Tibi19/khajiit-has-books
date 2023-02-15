package com.tam.tesbooks.presentation.screen.bookmarks

import com.tam.tesbooks.domain.model.bookmark.Bookmark

data class BookmarksState(
    val bookmarks: List<Bookmark> = emptyList(),
    val isLoading: Boolean = false,
    val canLoadMore: Boolean = false,
    val pagesLoaded: Int = 0
)
