package com.tam.tesbooks.presentation.reusable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.presentation.dialog.lists.AddBookToListDialog
import com.tam.tesbooks.ui.theme.KhajiitHasBooksTheme
import com.tam.tesbooks.util.*
import com.tam.tesbooks.R

@Composable
fun BookCardItem(
    bookInfo: BookInfo,
    bookLists: List<BookList>,
    onChangeBookList: (bookInfo: BookInfo, bookList: BookList) -> Unit,
    onClick: () -> Unit
) {

    val isListDialogOpenState = remember { mutableStateOf(false) }

    BookCard(
        bookInfo = bookInfo,
        bookLists = bookLists,
        isListDialogOpenState = isListDialogOpenState,
        onChangeBookList = onChangeBookList,
        onClick = onClick
    )

    AddBookToListDialog(
        isOpenState = isListDialogOpenState,
        bookInfo = bookInfo,
        onChangeBookList = onChangeBookList
    )

}

@Composable
private fun BookCard(
    bookInfo: BookInfo,
    bookLists: List<BookList>,
    isListDialogOpenState: MutableState<Boolean>,
    onChangeBookList: (bookInfo: BookInfo, bookList: BookList) -> Unit,
    onClick: () -> Unit
) =
    Card(
        elevation = ELEVATION_DEFAULT,
        modifier = Modifier
            .padding(top = PADDING_NORMAL)
            .padding(horizontal = PADDING_NORMAL)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(PADDING_NORMAL)
                .fillMaxWidth()
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = PADDING_NORMAL)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_written_by),
                    contentDescription = CONTENT_WRITTEN_BY,
                    modifier = Modifier
                        .padding(end = PADDING_X_SMALL, top = PADDING_XX_SMALL)
                        .size(SIZE_ICON_SMALL)
                )

                val author = bookInfo.metadata.author
                val category = bookInfo.metadata.category
                val authorAndCategoryText = "$author$TEXT_BETWEEN_AUTHOR_AND_CATEGORY$category"
                Text(
                    text = authorAndCategoryText,
                    color = MaterialTheme.colors.onSurface,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }

            Text(
                text = bookInfo.metadata.title.uppercase(),
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(bottom = PADDING_SMALL)
            )

            val descriptionText = bookInfo.metadata.description
            if(descriptionText.isNotEmpty()) {
                Text(text = bookInfo.metadata.description)
            }

            BookListsControlRow(
                bookInfo = bookInfo,
                buttons = setOf(
                    BookListOption.Viewed,
                    BookListOption.Favorite,
                    BookListOption.AddTo(onOpenAddToListDialog = { isListDialogOpenState.value = true })
                ),
                bookLists = bookLists,
                onChangeBookList = { bookList -> onChangeBookList(bookInfo, bookList) },
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .padding(top = PADDING_LARGE)
                    .fillMaxWidth()
            )

        }
    }

@Composable
@Preview
private fun BookCardPreview() =
    KhajiitHasBooksTheme {
        BookCard(
            bookInfo = getTestBookInfo(),
            bookLists = getTestBookLists(),
            isListDialogOpenState = remember { mutableStateOf(false) },
            onChangeBookList = { _, _ -> },
            onClick = {}
        )
    }

@Composable
private fun BookCardItemLegacy(
    bookInfo: BookInfo,
    bookLists: List<BookList>,
    onChangeBookList: (bookInfo: BookInfo, bookList: BookList) -> Unit,
    onClick: () -> Unit
) =
    Card(
        shape = RoundedCornerShape(Dimens.RADIUS_DEFAULT),
        elevation = Dimens.ELEVATION_DEFAULT,
        modifier = Dimens.PADDING_DEFAULT.clickable { onClick() }
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            Row(
                modifier = Dimens.PADDING_DEFAULT.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onChangeBookList(bookInfo, bookLists.getViewed()) },
                    modifier = Dimens.PADDING_SMALL
                ) {
                    Text("Read")
                }
                Button(
                    onClick = { onChangeBookList(bookInfo, bookLists.getFavorite()) },
                    modifier = Dimens.PADDING_SMALL
                ) {
                    Text("Favorite")
                }
                Button(
                    onClick = { /* isListDialogOpenState.value = true */ },
                    modifier = Dimens.PADDING_SMALL
                ) {
                    Text("Add to")
                }
            }

            Text(
                text = bookInfo.metadata.title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Dimens.PADDING_DEFAULT.fillMaxWidth()
            )
            Text(modifier = Dimens.PADDING_TEXT_OLD, text = bookInfo.metadata.author)
            Text(modifier = Dimens.PADDING_TEXT_OLD, text = bookInfo.metadata.category)
            Text(modifier = Dimens.PADDING_LAST_TEXT, text = bookInfo.metadata.description)

        }
    }