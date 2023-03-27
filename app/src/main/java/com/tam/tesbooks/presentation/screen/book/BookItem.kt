package com.tam.tesbooks.presentation.screen.book

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.google.accompanist.flowlayout.FlowRow
import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.domain.model.metadata.BookMetadata
import com.tam.tesbooks.presentation.common.item.BookParagraphItem
import com.tam.tesbooks.ui.theme.CustomColors
import com.tam.tesbooks.ui.theme.EBGaramondFontFamily
import com.tam.tesbooks.util.*
import kotlinx.coroutines.android.awaitFrame

@Composable
fun BookItem(
    book: Book,
    bookmarks: List<Bookmark>,
    bookViewModel: BookViewModel
) {
    val lazyListState = rememberLazyListState()
    val areParagraphsLoaded = remember {
        derivedStateOf {
            book.paragraphs.isNotEmpty() &&
            lazyListState.layoutInfo.totalItemsCount >= SIZE_BOOK_SCREEN_ITEMS_COUNT_WITH_NO_PARAGRAPHS + book.paragraphs.size
        }
    }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxSize()
    ) {
        val metadata = book.bookInfo.metadata

        item {

            Text(
                style = MaterialTheme.typography.h2,
                textAlign = TextAlign.Center,
                text = metadata.title.uppercase(),
                modifier = Modifier
                    .padding(vertical = PADDING_X_LARGE, horizontal = PADDING_XX_LARGE)
                    .fillMaxWidth()
            )

            BookDetails(metadata)

            TagsRow(tags = metadata.tags) { tag ->
                bookViewModel.onEvent(BookEvent.OnTagSearch(tag))
            }
        }

        items(book.paragraphs) { bookParagraph ->
            val bookmark = bookmarks.find { it.paragraph == bookParagraph }
            val isParagraphBookmarked = bookmark != null
            BookParagraphItem(
                bookParagraph = bookParagraph,
                onLongPressParagraph = {
                    val changeBookmarkEvent = BookEvent.OnChangeBookmark(bookParagraph)
                    bookViewModel.onEvent(changeBookmarkEvent)
                },
                shouldHighlightBackground = isParagraphBookmarked
            )
        }

        item {
            Spacer(modifier = Modifier.size(PADDING_NORMAL))
        }
    }

    LaunchedEffect(areParagraphsLoaded) {
        bookViewModel.scrollToParagraphPositionFlow.collect { paragraphPosition ->
            awaitFrame()
            lazyListState.animateScrollToItem(SIZE_BOOK_SCREEN_ITEMS_BEFORE_PARAGRAPHS + paragraphPosition)
        }
    }
}

@Composable
private fun BookDetails(metadata: BookMetadata) {
    val description = metadata.description.ifEmpty { TEXT_BOOK_DESCRIPTION_EMPTY_REPLACEMENT }
    val detailsIntrosAndContents = listOf(
        Pair(TEXT_BOOK_AUTHOR_INTRO, metadata.author),
        Pair(TEXT_BOOK_CATEGORY_INTRO, metadata.category),
        Pair(TEXT_BOOK_DESCRIPTION_INTRO, description)
    )
    detailsIntrosAndContents.forEach { introAndContent ->
        BookDetailsText(
            detailsIntro = introAndContent.first,
            detailsContent = introAndContent.second
        )
    }
}

@Composable
private fun BookDetailsText(detailsIntro: String, detailsContent: String) {
    val details = "$detailsIntro$detailsContent"
    val detailsStyles = detailsIntro.asBoldedStyles()
    val detailsAnnotatedString = AnnotatedString(text = details, spanStyles = detailsStyles)
    Text(
        text = detailsAnnotatedString,
        modifier = Modifier
            .padding(horizontal = PADDING_NORMAL)
            .padding(bottom = PADDING_BOTTOM_BOOK_DETAILS_TEXT)
    )
}

private fun String.asBoldedStyles() =
    listOf(
        AnnotatedString.Range(
            SpanStyle(fontWeight = FontWeight.Bold),
            start = 0,
            end = this.length
        )
    )

@Composable
private fun TagsRow(
    tags: List<String>,
    onTagClick: (tag: String) -> Unit
) {
    FlowRow(
        mainAxisSpacing = PADDING_SMALL,
        crossAxisSpacing = PADDING_SMALL,
        modifier = Modifier
            .padding(top = PADDING_XX_SMALL, bottom = PADDING_LARGE)
            .padding(horizontal = PADDING_NORMAL)
    ) {

        tags.forEach { tag ->
            BookTag(
                tagText = tag,
                onClick = { onTagClick(tag) }
            )
        }

    }
}

@Composable
private fun BookTag(
    tagText: String,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(color = MaterialTheme.colors.primary)
            .clickable { onClick() }
            .padding(vertical = PADDING_XX_SMALL, horizontal = PADDING_X_SMALL)
    ) {
        Text(
            text = tagText,
            style = MaterialTheme.typography.body1,
            color = CustomColors.colors.tagTextColor,
            fontFamily = EBGaramondFontFamily
        )
    }
}
