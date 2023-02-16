package com.tam.tesbooks.presentation.screen.bookmarks

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.tam.tesbooks.presentation.reusable.BarRow
import com.tam.tesbooks.presentation.reusable.LoadingMoreIndicator
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.ui.theme.CustomColors
import com.tam.tesbooks.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarksScreen(
    bookmarksViewModel: BookmarksViewModel = hiltViewModel(),
    goToBookScreen: (bookId: Int) -> Unit = {},
    goToPreviousScreen: () -> Unit = {},
) {
    val state = bookmarksViewModel.state

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)
    ) {
        stickyHeader {
            BarRow(onOptionalBackIcon = goToPreviousScreen) {
                SectionText(
                    text = TEXT_BOOKMARKS,
                    style = MaterialTheme.typography.h4
                )
            }
        }

        item {
            if(state.bookmarks.isNotEmpty()) return@item
            NoBookmarksPrompt()
        }

        items(
            key = { bookmark -> bookmark.uuid },
            items = state.bookmarks
        ) {bookmark ->
            BookmarkItem(
                bookmark = bookmark,
                goToBookScreen = goToBookScreen,
                deleteBookmark = {
                    val deleteBookmarkEvent = BookmarksEvent.OnDeleteBookmark(bookmark)
                    bookmarksViewModel.onEvent(deleteBookmarkEvent)
                }
            )
            if(state.bookmarks.last() != bookmark) {
                Divider(
                    thickness = SIZE_BOOKMARKS_DIVIDER,
                    color = CustomColors.colors.onSurfaceVariant,
                    modifier = Modifier.animateItemPlacement()
                )
            } else {
                decideLoadingMoreBookmarks(state, bookmarksViewModel)
            }
        }

        item {
            if (!state.isLoading) return@item
            LoadingMoreIndicator()
        }

    }

}

@Composable
private fun NoBookmarksPrompt() {
    Text(
        text = "$TEXT_NO_BOOKMARKS\n\n$TEXT_ADD_BOOKMARKS",
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(PADDING_NORMAL)
    )
}

private fun decideLoadingMoreBookmarks(
    state: BookmarksState,
    bookmarksViewModel: BookmarksViewModel
) {
    val shouldLoadMore = state.canLoadMore && !state.isLoading
    if(shouldLoadMore) {
        val loadMoreEvent = BookmarksEvent.OnLoadMoreBookmarks
        bookmarksViewModel.onEvent(loadMoreEvent)
    }
}
