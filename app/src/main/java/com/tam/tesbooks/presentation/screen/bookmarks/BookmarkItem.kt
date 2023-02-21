@file:OptIn(ExperimentalMaterialApi::class)

package com.tam.tesbooks.presentation.screen.bookmarks

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.presentation.reusable.BookParagraphItem
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.util.*

@ExperimentalFoundationApi
@Composable
fun LazyItemScope.BookmarkItem(
    bookmark: Bookmark,
    goToBookScreenAndParagraph: (bookId: Int, paragraphPosition: Int) -> Unit,
    deleteBookmark: () -> Unit
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            val isDismissed = it == DismissValue.DismissedToStart
            if (isDismissed) {
                deleteBookmark()
            }
            isDismissed
        }
    )
    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        dismissThresholds = { FractionalThreshold(THRESHOLD_DISMISS_BOOKMARK) },
        background = { BookmarkDismissBackground(isDismissed = dismissState.targetValue == DismissValue.DismissedToStart) },
        dismissContent = { BookmarkItemContent(bookmark = bookmark, goToBookScreenAndParagraph = goToBookScreenAndParagraph) },
        modifier = Modifier.animateItemPlacement()
    )
}

@Composable
private fun BookmarkDismissBackground(isDismissed: Boolean) {
    val dismissedColor = MaterialTheme.colors.primaryVariant
    val defaultColor = MaterialTheme.colors.primary
    val backgroundColor by animateColorAsState(if (isDismissed) dismissedColor else defaultColor)
    val dismissedScale by animateFloatAsState(if (isDismissed) SCALE_DISMISSED_BOOKMARK_BACKGROUND else SCALE_DISMISSING_BOOKMARK_BACKGROUND)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(end = PADDING_SMALL)
                .align(Alignment.CenterEnd)
        ) {

            Text(
                text = TEXT_DELETE,
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.button,
                modifier = Modifier
                    .padding(bottom = PADDING_XX_SMALL)
                    .scale(dismissedScale)
            )

            Image(
                painter = painterResource(R.drawable.ic_trash),
                contentDescription = CONTENT_DELETE_BOOKMARK,
                modifier = Modifier
                    .size(SIZE_ICON_NORMAL)
                    .scale(dismissedScale)
            )

        }
    }
}

@Composable
private fun BookmarkItemContent(
    bookmark: Bookmark,
    goToBookScreenAndParagraph: (bookId: Int, paragraphPosition: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { goToBookScreenAndParagraph(bookmark.paragraph.bookId, bookmark.paragraph.position) }
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondary)
            .padding(top = PADDING_NORMAL, bottom = PADDING_SMALL)
    ) {
        BookmarkHeader(
            bookmark = bookmark,
            goToBookScreenAndParagraph = goToBookScreenAndParagraph
        )

        BookParagraphItem(bookParagraph = bookmark.paragraph)
    }
}

@Composable
private fun BookmarkHeader(
    bookmark: Bookmark,
    goToBookScreenAndParagraph: (bookId: Int, paragraphPosition: Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = PADDING_NORMAL)
            .fillMaxWidth()
            .padding(bottom = PADDING_X_SMALL)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_bookmarks_filled),
            contentDescription = CONTENT_BOOKMARK,
            modifier = Modifier
                .padding(end = PADDING_XX_SMALL)
                .size(SIZE_ICON_NORMAL)
                .offset(x = -PADDING_XX_SMALL)
        )

        SectionText(
            text = "$TEXT_BOOKMARK_TITLE_START${bookmark.bookTitle}",
            style = MaterialTheme.typography.h6
        )

        Image(
            painter = painterResource(id = R.drawable.ic_forward),
            contentDescription = CONTENT_FORWARD_BOOKMARKS,
            modifier = Modifier
                .size(SIZE_ICON_NORMAL)
                .clickable { goToBookScreenAndParagraph(bookmark.paragraph.bookId, bookmark.paragraph.position) }
        )
    }
}