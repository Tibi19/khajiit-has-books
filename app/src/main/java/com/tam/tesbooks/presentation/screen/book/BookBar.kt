package com.tam.tesbooks.presentation.screen.book

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.util.*

@Composable
fun BookBar(
    book: Book,
    goToPreviousScreen: () -> Unit,
    bookLists: List<BookList> = getTestBookLists(),
    onChangeBookList: (bookInfo: BookInfo, bookList: BookList) -> Unit = { _, _ -> printTest("Trying to change book list") },
    onNewList: (name: String) -> Unit = { printTest("Trying to create new book list") },
) {
    Row(
        modifier = Dimens.PADDING_BOTTOM_SMALL
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { goToPreviousScreen() }) {
            Icon(
                imageVector = Icons.Default.ArrowBackIos,
                contentDescription = CONTENT_GO_BACK,
                tint = MaterialTheme.colors.primary,
                modifier = Dimens.SIZE_BOOK_BAR_ICON
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        val outlinedButtonColors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.primary
        )
        val outlinedButtonBorder = BorderStroke(
            width = Dimens.STROKE_WIDTH_OUTLINED_BUTTON,
            color = MaterialTheme.colors.primary
        )
        OutlinedButton(
            onClick = { onChangeBookList(book.bookInfo, bookLists.getRead()) },
            colors = outlinedButtonColors,
            modifier = Dimens.PADDING_SMALL,
            border = outlinedButtonBorder
        ) {
            Text("Read")
        }

        OutlinedButton(
            onClick = { onChangeBookList(book.bookInfo, bookLists.getFavorite()) },
            colors = outlinedButtonColors,
            modifier = Dimens.PADDING_SMALL,
            border = outlinedButtonBorder
        ) {
            Text("Favorite")
        }

        OutlinedButton(
            onClick = { printTest("Show add to list dialog in book screen") },
            colors = outlinedButtonColors,
            modifier = Dimens.PADDING_SMALL,
            border = outlinedButtonBorder
        ) {
            Text("Add to")
        }
    }
}