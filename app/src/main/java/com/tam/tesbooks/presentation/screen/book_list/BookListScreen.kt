package com.tam.tesbooks.presentation.screen.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.tam.tesbooks.domain.model.listing_modifier.BookListSort
import com.tam.tesbooks.presentation.reusable.BookCardItem
import com.tam.tesbooks.presentation.reusable.OnErrorEffect

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
        item {
            BookListBar(
                bookListName = state.bookList?.name ?: "",
                onSortChange = { listSort: BookListSort ->
                    val sortChangeEvent = BookListEvent.OnSortChange(listSort)
                    bookListViewModel.onEvent(sortChangeEvent)
                },
                goToPreviousScreen = goToPreviousScreen
            )
        }

        items(state.bookInfos) { bookInfo ->
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

    }
}