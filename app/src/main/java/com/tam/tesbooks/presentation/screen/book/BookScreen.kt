package com.tam.tesbooks.presentation.screen.book

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.tam.tesbooks.presentation.common.effect.OnErrorEffect
import com.tam.tesbooks.util.*
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BookScreen(
    bookViewModel: BookViewModel = hiltViewModel(),
    goToLibraryWithSearch: (tag: String, category: String) -> Unit,
    goToPreviousScreen: () -> Unit
) {
    val state = bookViewModel.state

    OnErrorEffect(errorFlow = bookViewModel.errorFlow)

    LaunchedEffect(key1 = true) {
        bookViewModel.searchLibraryFlow.collect { (tag, category) ->
            goToLibraryWithSearch(tag, category)
        }
    }

    if(state.booksStack.isEmpty()) return

    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.secondary)
    ) {

        val currentBookInfo = state.booksStack[pagerState.currentPage].bookInfo
        BookBar(
            bookInfo = currentBookInfo,
            goToPreviousScreen = goToPreviousScreen,
            bookLists = bookViewModel.state.bookLists,
            onChangeBookList = { passedBookInfo, bookList ->
                val onChangeBookListEvent = BookEvent.OnChangeBookList(passedBookInfo, bookList)
                bookViewModel.onEvent(onChangeBookListEvent)
            }
        )

        HorizontalPager(
            count = state.booksStack.size,
            state = pagerState
        ) { page ->
            val currentBook = state.booksStack[page]
            BookItem(
                book = currentBook,
                bookmarks = state.bookmarks,
                bookViewModel = bookViewModel
            )
        }
    }

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collectLatest { page ->
                val bookSwipeEvent = BookEvent.OnBookSwipe(swipedToBookIndex = page)
                bookViewModel.onEvent(bookSwipeEvent)
            }
    }
}