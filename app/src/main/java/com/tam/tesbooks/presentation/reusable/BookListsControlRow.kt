package com.tam.tesbooks.presentation.reusable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.util.*

sealed class BookListOption {
    object Viewed: BookListOption()
    object Favorite: BookListOption()
    object ReadLater: BookListOption()
    data class AddTo(val onOpenAddToListDialog: () -> Unit): BookListOption()
}

internal data class BookListOptionData(
    val bookInfo: BookInfo,
    val bookLists: List<BookList>,
    val onChangeBookList: (bookList: BookList) -> Unit
)

@Composable
fun BookListsControlRow(
    bookInfo: BookInfo,
    buttons: Set<BookListOption>,
    bookLists: List<BookList>,
    onChangeBookList: (bookList: BookList) -> Unit,
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start
) =
    Row(
        verticalAlignment = verticalAlignment,
        horizontalArrangement = horizontalArrangement,
        modifier = modifier
    ) {

        buttons
            .filterNot { it is BookListOption.AddTo }
            .forEach { option ->
                val optionData = BookListOptionData(bookInfo, bookLists, onChangeBookList)
                when(option) {
                    is BookListOption.ReadLater -> ReadLaterButton(optionData = optionData)
                    is BookListOption.Viewed -> ViewedButton(optionData = optionData)
                    is BookListOption.Favorite -> FavoriteButton(optionData = optionData)
                    else -> {}
                }
            }

        buttons
            .find { it is BookListOption.AddTo }
            ?.let { bookListOption -> AddToButton(addToOption = bookListOption as BookListOption.AddTo) }
    }

@Composable
private fun ViewedButton(optionData: BookListOptionData) {
    val viewedBooksList = optionData.bookLists.getViewed()
    val (markViewedIconId, markViewedIconDescription) = getMarkViewedIconIdAndDescription(optionData.bookInfo, viewedBooksList)
    Image(
        painter = painterResource(id = markViewedIconId),
        contentDescription = markViewedIconDescription,
        modifier = Modifier
            .padding(top = PADDING_TOP_ICON_MARK_VIEWED_BOOK, end = PADDING_LARGE)
            .size(SIZE_ICON_MARK_VIEWED_BOOK)
            .clickable { optionData.onChangeBookList(viewedBooksList) }
    )
}

@Composable
private fun ReadLaterButton(optionData: BookListOptionData) {
    val readLaterBookList = optionData.bookLists.getReadLater()
    val (readLaterIconId, readLaterIconDescription) = getReadLaterIconIdAndDescription(optionData.bookInfo, readLaterBookList)
    Image(
        painter = painterResource(id = readLaterIconId),
        contentDescription = readLaterIconDescription,
        modifier = Modifier
            .padding(top = PADDING_TOP_ICON_READ_LATER, end = PADDING_LARGE)
            .size(SIZE_ICON_READ_LATER)
            .clickable { optionData.onChangeBookList(readLaterBookList) }
    )
}

@Composable
private fun FavoriteButton(optionData: BookListOptionData) {
    val favoriteBooksList = optionData.bookLists.getFavorite()
    val (favoriteIconId, favoriteIconDescription) = getFavoriteIconIdAndDescription(optionData.bookInfo, favoriteBooksList)
    Image(
        painter = painterResource(id = favoriteIconId),
        contentDescription = favoriteIconDescription,
        modifier = Modifier
            .padding(end = PADDING_LARGE)
            .size(SIZE_ICON_NORMAL)
            .clickable { optionData.onChangeBookList(favoriteBooksList) }
    )
}

@Composable
private fun AddToButton(addToOption: BookListOption.AddTo) {
    Image(
        painter = painterResource(id = R.drawable.ic_save),
        contentDescription = CONTENT_SAVE_TO_LIST,
        modifier = Modifier
            .size(SIZE_ICON_NORMAL)
            .clickable { addToOption.onOpenAddToListDialog() }
    )
}