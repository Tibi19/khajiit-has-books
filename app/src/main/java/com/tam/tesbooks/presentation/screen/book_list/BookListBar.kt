package com.tam.tesbooks.presentation.screen.book_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.domain.model.listing_modifier.BookListSort
import com.tam.tesbooks.presentation.dialog.SortListDialog
import com.tam.tesbooks.presentation.reusable.BarRow
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.util.CONTENT_GO_TO_PREVIOUS_SCREEN
import com.tam.tesbooks.util.CONTENT_SORT_LIST
import com.tam.tesbooks.util.SIZE_ICON_LARGE

@Composable
fun BookListBar(
    bookListName: String,
    bookListSort: BookListSort,
    onSortChange: (newListSort: BookListSort) -> Unit,
    goToPreviousScreen: () -> Unit
) {
    val isSortDialogOpenState = remember { mutableStateOf(false) }

    BarRow(
        onOptionalBackIcon = goToPreviousScreen,
        backIconContentDescription = CONTENT_GO_TO_PREVIOUS_SCREEN,
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        SectionText(
            text = bookListName,
            style = MaterialTheme.typography.h4
        )

        Image(
            painter = painterResource(id = R.drawable.ic_sort),
            contentDescription = CONTENT_SORT_LIST,
            modifier = Modifier
                .size(SIZE_ICON_LARGE)
                .clickable { isSortDialogOpenState.value = true }
        )
    }

    SortListDialog(
        isOpenState = isSortDialogOpenState,
        bookListSort = bookListSort,
        onSubmitSort = { newListSort -> onSortChange(newListSort) }
    )
}