package com.tam.tesbooks.presentation.screen.bookmarks

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BookmarksScreen(
    bookmarksViewModel: BookmarksViewModel = hiltViewModel(),
    goToBookScreen: (bookId: Int) -> Unit = {}
) {

}