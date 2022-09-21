package com.tam.tesbooks.presentation.screen.library

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.tam.tesbooks.presentation.reusable.BookCardItem
import com.tam.tesbooks.util.Dimens

@Preview
@Composable
fun LibraryScreen(
    libraryViewModel: LibraryViewModel = hiltViewModel(),
    goToBookScreen: (bookId: Int) -> Unit = {}
) {
    val state = libraryViewModel.state
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
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
        
        item {
            Spacer(modifier = Dimens.SIZE_BOTTOM_BAR_HEIGHT)
        }
    }
}