package com.tam.tesbooks.presentation.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.tam.tesbooks.domain.model.listing_modifier.BookListSort
import com.tam.tesbooks.domain.model.listing_modifier.Sort
import com.tam.tesbooks.util.TEXT_SORT_LIST

@Composable
fun SortListDialog(
    isOpenState: MutableState<Boolean>,
    bookListSort: BookListSort,
    onSubmitSort: (newListSort: BookListSort) -> Unit
) {
    if(!isOpenState.value) return

    BottomSheetDialogProvider.showDialog(
        isOpenState = isOpenState,
        content = {
            SortListDialogContent(
                isOpenState = isOpenState,
                bookListSort = bookListSort,
                onSubmitSort = { newListSort -> onSubmitSort(newListSort) }
            )
        }
    )

}

@Composable
private fun SortListDialogContent(
    isOpenState: MutableState<Boolean>,
    bookListSort: BookListSort,
    onSubmitSort: (newListSort: BookListSort) -> Unit
) {
    val bookListSortState = remember { mutableStateOf(bookListSort) }
    BottomSheetDialog(
        title = TEXT_SORT_LIST,
        isOpenState = isOpenState,
        onSubmit = { onSubmitSort(bookListSortState.value) }
    ) {
        val options = Sort.values()
        options.forEach { sort ->
            // TODO: Add RadioOption for sort
        }
        // TODO: Add reverse order switch
    }

}