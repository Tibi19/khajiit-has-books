package com.tam.tesbooks.presentation.screen.book

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.presentation.dialog.lists.AddBookToListDialog
import com.tam.tesbooks.presentation.reusable.BarRow
import com.tam.tesbooks.presentation.reusable.getFavoriteIconIdAndDescription
import com.tam.tesbooks.presentation.reusable.getMarkViewedIconIdAndDescription
import com.tam.tesbooks.util.*

@Composable
fun BookBar(
    bookInfo: BookInfo,
    goToPreviousScreen: () -> Unit,
    bookLists: List<BookList>,
    onChangeBookList: (bookInfo: BookInfo, bookList: BookList) -> Unit,
) {
    val isListDialogOpenState = remember { mutableStateOf(false) }

    val viewedBooksList = bookLists.getViewed()
    val favoriteBooksList = bookLists.getFavorite()
    val (markViewedIconId, markViewedIconDescription) = getMarkViewedIconIdAndDescription(bookInfo, viewedBooksList)
    val (favoriteIconId, favoriteIconDescription) = getFavoriteIconIdAndDescription(bookInfo, favoriteBooksList)

    BarRow(
        onOptionalBackIcon = goToPreviousScreen,
        backIconContentDescription = CONTENT_GO_TO_PREVIOUS_SCREEN
    ) {

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = markViewedIconId),
            contentDescription = markViewedIconDescription,
            modifier = Modifier
                .padding(top = PADDING_TOP_ICON_MARK_VIEWED_BOOK, end = PADDING_LARGE)
                .size(SIZE_ICON_MARK_VIEWED_BOOK)
                .clickable { onChangeBookList(bookInfo, viewedBooksList) }
        )

        Image(
            painter = painterResource(id = favoriteIconId),
            contentDescription = favoriteIconDescription,
            modifier = Modifier
                .padding(end = PADDING_LARGE)
                .size(SIZE_ICON_NORMAL)
                .clickable { onChangeBookList(bookInfo, favoriteBooksList) }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_save),
            contentDescription = CONTENT_SAVE_TO_LIST,
            modifier = Modifier
                .size(SIZE_ICON_NORMAL)
                .clickable { isListDialogOpenState.value = true }
        )
    }

    AddBookToListDialog(
        isOpenState = isListDialogOpenState,
        bookInfo = bookInfo,
        onChangeBookList = onChangeBookList
    )

}