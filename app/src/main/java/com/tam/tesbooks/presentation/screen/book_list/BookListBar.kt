package com.tam.tesbooks.presentation.screen.book_list

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.tam.tesbooks.domain.model.listing_modifier.BookListSort
import com.tam.tesbooks.presentation.reusable.BarRow
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.util.CONTENT_GO_TO_PREVIOUS_SCREEN

@Composable
fun BookListBar(
    bookListName: String,
    onSortChange: (listSort: BookListSort) -> Unit,
    goToPreviousScreen: () -> Unit
) {
    val isSortDialogOpenState = remember { mutableStateOf(false) }

    BarRow(
        onOptionalBackIcon = goToPreviousScreen,
        backIconContentDescription = CONTENT_GO_TO_PREVIOUS_SCREEN,
        backgroundColor = MaterialTheme.colors.secondary
    ) {
        SectionText(text = bookListName)
    }
}