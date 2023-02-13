package com.tam.tesbooks.presentation.screen.bookmarks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.tam.tesbooks.presentation.reusable.BarRow
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.ui.theme.CustomColors
import com.tam.tesbooks.util.SIZE_BOOKMARKS_DIVIDER
import com.tam.tesbooks.util.TEXT_BOOKMARKS

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
        item {
            BarRow(onOptionalBackIcon = goToPreviousScreen) {
                SectionText(
                    text = TEXT_BOOKMARKS,
                    style = MaterialTheme.typography.h4
                )
            }
        }

        items(state.bookmarks) {bookmark ->
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
                    color = CustomColors.colors.onSurfaceVariant
                )
            }
        }

    }

}
