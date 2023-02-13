package com.tam.tesbooks.presentation.screen.bookmarks

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.presentation.reusable.BookParagraphItem
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.util.*

@Composable
fun BookmarkItem(
    bookmark: Bookmark,
    goToBookScreen: (bookId: Int) -> Unit,
    deleteBookmark: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { goToBookScreen(bookmark.paragraph.bookId) }
            .fillMaxWidth()
            .padding(top = PADDING_NORMAL, bottom = PADDING_SMALL)
    ) {
        BookmarkHeader(
            bookmark = bookmark,
            goToBookScreen = goToBookScreen
        )

        BookParagraphItem(bookParagraph = bookmark.paragraph)
    }
}

@Composable
private fun BookmarkDismissBackground() {
    // TODO: https://developer.android.com/training/wearables/compose/swipe-to-dismiss
    // Add keys to items of lazy column parent: https://daanidev.medium.com/swipe-to-delete-in-jetpack-compose-android-ca935a209c61
}

@Composable
private fun BookmarkItemContent(
    bookmark: Bookmark,
    goToBookScreen: (bookId: Int) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { goToBookScreen(bookmark.paragraph.bookId) }
            .fillMaxWidth()
            .padding(top = PADDING_NORMAL, bottom = PADDING_SMALL)
    ) {
        BookmarkHeader(
            bookmark = bookmark,
            goToBookScreen = goToBookScreen
        )

        BookParagraphItem(bookParagraph = bookmark.paragraph)
    }
}

@Composable
private fun BookmarkHeader(
    bookmark: Bookmark,
    goToBookScreen: (bookId: Int) -> Unit
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
            text = "TODO: Needs Book Title",
            style = MaterialTheme.typography.h6
        )

        Image(
            painter = painterResource(id = R.drawable.ic_forward),
            contentDescription = CONTENT_FORWARD_BOOKMARKS,
            modifier = Modifier
                .size(SIZE_ICON_NORMAL)
                .clickable { goToBookScreen(bookmark.paragraph.bookId) }
        )
    }
}