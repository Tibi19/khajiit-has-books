package com.tam.tesbooks.presentation.reusable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.max
import coil.compose.AsyncImage
import com.tam.tesbooks.R
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.presentation.dialog.lists.AddBookToListDialog
import com.tam.tesbooks.util.*

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
        Column(modifier = Modifier.fillMaxWidth()) {
            BookCardBanner(
                bookTitle = bookInfo.metadata.title,
                bannerUrl = bookInfo.metadata.banner
            )
            BookCardContent(
                bookInfo = bookInfo,
                bookLists = bookLists,
                isListDialogOpenState = isListDialogOpenState,
                onChangeBookList = onChangeBookList
            )
        }
    }

@Composable
fun BookCardBanner(bookTitle: String, bannerUrl: String) {
    AsyncImage(
        model = bannerUrl,
        contentScale = ContentScale.Crop,
        contentDescription = "$CONTENT_BANNER_IMAGE_START$bookTitle",
        alignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(LocalBannerHeight.current)
    )
}

private object LocalBannerHeight {
    private var bannerHeight: Dp? = null
    val current: Dp
        @Composable get() = bannerHeight ?: run {
            bannerHeight = getBannerHeight()
            bannerHeight!!
        }

    @Composable
    private fun getBannerHeight(): Dp = getContentHeight() * RATIO_BOOK_ITEM_BANNER_TO_CONTENT_HEIGHTS

    @Composable
    // Content height is an estimate if both title and description are single lines
    private fun getContentHeight(): Dp {
        val authorIconHeight = PADDING_XX_SMALL + SIZE_ICON_SMALL
        val authorAndDescriptionHeight = SIZE_TEXT_X_SMALL.toDp()
        val authorRowHeight = max(authorIconHeight, authorAndDescriptionHeight)
        val titleHeight = SIZE_TEXT_LARGE.toDp()
        val descriptionHeight = SIZE_TEXT_X_SMALL.toDp()
        return authorRowHeight + titleHeight + descriptionHeight + SIZE_ICON_NORMAL + 3 * PADDING_NORMAL + PADDING_SMALL + PADDING_LARGE
    }

    @Composable
    private fun TextUnit.toDp() = with(LocalDensity.current) { toDp() }

    private operator fun Int.times(dp: Dp): Dp = Dp(value = dp.value * this)
}

@Composable
private fun BookCardContent(
    bookInfo: BookInfo,
    bookLists: List<BookList>,
    isListDialogOpenState: MutableState<Boolean>,
    onChangeBookList: (bookInfo: BookInfo, bookList: BookList) -> Unit
) =
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
            style = MaterialTheme.typography.h3
        )

        val descriptionText = bookInfo.metadata.description
        if(descriptionText.isNotEmpty()) {
            Spacer(modifier = Modifier.height(PADDING_SMALL))
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
