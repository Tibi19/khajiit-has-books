package com.tam.tesbooks.presentation.screen.book

import androidx.compose.foundation.layout.Spacer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.presentation.dialog.lists.AddBookToListDialog
import com.tam.tesbooks.presentation.reusable.BarRow
import com.tam.tesbooks.presentation.reusable.BookListOption
import com.tam.tesbooks.presentation.reusable.BookListsControlRow
import com.tam.tesbooks.util.CONTENT_GO_TO_PREVIOUS_SCREEN

@Composable
fun BookBar(
    bookInfo: BookInfo,
    goToPreviousScreen: () -> Unit,
    bookLists: List<BookList>,
    onChangeBookList: (bookInfo: BookInfo, bookList: BookList) -> Unit,
) {
    val isListDialogOpenState = remember { mutableStateOf(false) }

    BarRow(
        onOptionalBackIcon = goToPreviousScreen,
        backIconContentDescription = CONTENT_GO_TO_PREVIOUS_SCREEN
    ) {

        Spacer(modifier = Modifier.weight(1f))

        BookListsControlRow(
            bookInfo = bookInfo,
            buttons = setOf(
                BookListOption.Viewed,
                BookListOption.Favorite,
                BookListOption.AddTo(onOpenAddToListDialog = { isListDialogOpenState.value = true })
            ),
            bookLists = bookLists,
            onChangeBookList = { bookList -> onChangeBookList(bookInfo, bookList) }
        )
    }

    AddBookToListDialog(
        isOpenState = isListDialogOpenState,
        bookInfo = bookInfo,
        onChangeBookList = onChangeBookList
    )

}