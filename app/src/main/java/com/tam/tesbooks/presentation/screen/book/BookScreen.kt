package com.tam.tesbooks.presentation.screen.book

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.presentation.reusable.BookParagraphItem
import com.tam.tesbooks.presentation.reusable.OnErrorEffect
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

    Column {

        BookBar(
            book = state.booksStack[pagerState.currentPage],
            goToPreviousScreen = goToPreviousScreen
        )

        HorizontalPager(
            count = state.booksStack.size,
            state = pagerState
        ) { page ->
            val currentBook = state.booksStack[page]
            BookItem(
                book = currentBook,
                bookViewModel = bookViewModel
            )
        }
    }

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }
            .collectLatest { page ->
                // Book swipe event should happen only on page change, so return if page is 0
                if (page == 0) return@collectLatest
                val bookSwipeEvent = BookEvent.OnBookSwipe(swipedToBookIndex = page)
                bookViewModel.onEvent(bookSwipeEvent)
            }
    }
}

@Composable
private fun BookItem(
    book: Book,
    bookViewModel: BookViewModel
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        val metadata = book.bookInfo.metadata

        item {
            Spacer(modifier = Dimens.PADDING_DEFAULT)

            Text(
                modifier = Dimens.PADDING_DEFAULT.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                text = metadata.title
            )

            Text(modifier = Dimens.PADDING_TEXT, text = metadata.author)

            TagsRow(tags = metadata.tags) { tag ->
                bookViewModel.onEvent(BookEvent.OnTagSearch(tag))
            }

            Spacer(modifier = Dimens.PADDING_DEFAULT)
        }

        items(book.paragraphs) { bookParagraph ->
            BookParagraphItem(
                bookParagraph = bookParagraph
            )
        }

        item {
            Spacer(modifier = Dimens.PADDING_DEFAULT)
        }
    }
}

@Composable
private fun TagsRow(
    tags: List<String>,
    onTagClick: (tag: String) -> Unit
) {
    FlowRow(
        mainAxisSpacing = Dimens.SPACING_TAGS_FLOW_ROW,
        crossAxisSpacing = Dimens.SPACING_TAGS_FLOW_ROW,
        modifier = Dimens.PADDING_DEFAULT
    ) {
        tags.forEach { tag ->
            Button(
                contentPadding = Dimens.PADDING_VALUES_TAG_BUTTON,
                onClick = { onTagClick(tag) }
            ) {
                Text(
                    text = tag,
                    fontSize = Dimens.SIZE_TAG_TEXT
                )
            }
        }
    }
}