package com.tam.tesbooks.presentation.screen.book

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.presentation.reusable.BookParagraphItem
import com.tam.tesbooks.presentation.reusable.OnErrorEffect
import com.tam.tesbooks.util.Dimens
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BookScreen(
    bookViewModel: BookViewModel = hiltViewModel(),
    goToLibraryWithSearch: (tag: String, category: String) -> Unit
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