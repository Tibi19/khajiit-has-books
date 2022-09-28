package com.tam.tesbooks.presentation.reusable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.util.*

@Preview(showBackground = true)
@Composable
fun BookCardItem(
    bookInfo: BookInfo = getTestBookInfo(),
    bookLists: List<BookList> = getTestBookLists(),
    onChangeBookList: (bookInfo: BookInfo, bookList: BookList) -> Unit = { _, _ -> printTest("Trying to change book list") },
    onNewList: (name: String) -> Unit = { printTest("Trying to create new book list") },
    onClick: () -> Unit = { printTest("Trying to open book ${bookInfo.metadata.title}") }
) {

    val context = LocalContext.current

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
                    onClick = { onChangeBookList(bookInfo, bookLists.getRead()) },
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
                    onClick = { showToast(context, "Should show add to list dialog") },
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
            Text(modifier = Dimens.PADDING_TEXT, text = bookInfo.metadata.author)
            Text(modifier = Dimens.PADDING_TEXT, text = bookInfo.metadata.category)
            Text(modifier = Dimens.PADDING_LAST_TEXT, text = bookInfo.metadata.description)

        }
    }

}