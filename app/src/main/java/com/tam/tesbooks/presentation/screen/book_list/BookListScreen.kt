package com.tam.tesbooks.presentation.screen.book_list

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.tam.tesbooks.presentation.common.LoadingMoreIndicator
import com.tam.tesbooks.presentation.common.effect.OnErrorEffect
import com.tam.tesbooks.presentation.common.item.BookCardItem
import com.tam.tesbooks.util.PADDING_NORMAL

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookListScreen(
    bookListViewModel: BookListViewModel = hiltViewModel(),
    goToBookScreen: (bookId: Int) -> Unit = {},
    goToPreviousScreen: () -> Unit
) {
    val state = bookListViewModel.state

    OnErrorEffect(errorFlow = bookListViewModel.errorFlow)

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colors.secondaryVariant)
            .fillMaxSize()
    ) {
        stickyHeader {
            BookListBar(
                bookListName = state.bookList?.name ?: "",
                bookListSort = state.listSort,
                onSortChange = { listSort ->
                    val sortChangeEvent = BookListEvent.OnSortChange(listSort)
                    bookListViewModel.onEvent(sortChangeEvent)
                },
                goToPreviousScreen = goToPreviousScreen
            )
        }

        items(
            key = { bookInfo -> bookInfo.bookId },
            items = state.bookInfos,
        ) { bookInfo ->
            BookCardItem(
                bookInfo = bookInfo,
                bookLists = state.bookLists,
                onChangeBookList = { passedBookInfo, bookList ->
                    val onChangeBookListEvent = BookListEvent.OnChangeBookList(passedBookInfo, bookList)
                    bookListViewModel.onEvent(onChangeBookListEvent)
                },
                onClick = { goToBookScreen(bookInfo.bookId) }
            )
        }

        item {
            Spacer(Modifier.height(PADDING_NORMAL))

            decideLoadingMoreBookInfos(state, bookListViewModel)
            if (!state.isLoading) return@item
            LoadingMoreIndicator()
        }

    }
}

private fun decideLoadingMoreBookInfos(
    state: BookListState,
    bookListViewModel: BookListViewModel
) {
    val shouldLoadMore = state.canLoadMore && !state.isLoading
    if(shouldLoadMore) {
        val loadMoreEvent = BookListEvent.OnLoadMoreBookInfos
        bookListViewModel.onEvent(loadMoreEvent)
    }
}
