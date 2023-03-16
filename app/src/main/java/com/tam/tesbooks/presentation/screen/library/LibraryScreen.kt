package com.tam.tesbooks.presentation.screen.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.tam.tesbooks.presentation.reusable.BookCardItem
import com.tam.tesbooks.presentation.reusable.OnErrorEffect

@Preview
@Composable
fun LibraryScreen(
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    goToBookScreen: (bookId: Int) -> Unit = {}
) {
    val state = libraryViewModel.state

    OnErrorEffect(errorFlow = libraryViewModel.errorFlow)

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colors.secondaryVariant)
            .fillMaxSize()
    ) {
        item {
            LibrarySearchBar(libraryViewModel)
        }

        items(state.bookInfos) { bookInfo ->
            BookCardItem(
                bookInfo = bookInfo,
                bookLists = state.bookLists,
                onChangeBookList = { passedBookInfo, bookList ->
                    val onChangeBookListEvent = LibraryEvent.OnChangeBookList(passedBookInfo, bookList)
                    libraryViewModel.onEvent(onChangeBookListEvent)
                },
                onClick = { goToBookScreen(bookInfo.bookId) }
            )
        }

    }

}