package com.tam.tesbooks.presentation.screen.library

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.tam.tesbooks.presentation.common.LoadingMoreIndicator
import com.tam.tesbooks.presentation.common.OnErrorEffect
import com.tam.tesbooks.presentation.common.item.BookCardItem
import com.tam.tesbooks.util.PADDING_NORMAL

@OptIn(ExperimentalFoundationApi::class)
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
        stickyHeader {
            LibrarySearchBar(libraryViewModel)
        }

        items(
            key = { bookInfo -> bookInfo.bookId },
            items = state.bookInfos
        ) { bookInfo ->
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
            Spacer(Modifier.height(PADDING_NORMAL))

            decideLoadingMoreBookInfos(state, libraryViewModel)
            if (!state.isLoading) return@item
            LoadingMoreIndicator()
        }
    }

}

private fun decideLoadingMoreBookInfos(
    state: LibraryState,
    bookListViewModel: LibraryViewModel
) {
    val shouldLoadMore = state.canLoadMore && !state.isLoading
    if(shouldLoadMore) {
        val loadMoreEvent = LibraryEvent.OnLoadMoreBookInfos
        bookListViewModel.onEvent(loadMoreEvent)
    }
}