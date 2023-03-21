package com.tam.tesbooks.presentation.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.tam.tesbooks.domain.model.listing_modifier.BookListSort
import com.tam.tesbooks.domain.model.listing_modifier.Sort
import com.tam.tesbooks.util.*

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
            val isSortSelected = sort == bookListSortState.value.sortBy
            RadioOption(
                text = getSortText(sort),
                isSelected = isSortSelected,
                onSelect = { selectSort(bookListSortState, sort) },
                modifier = Modifier.padding(bottom = PADDING_X_SMALL)
            )
        }

        val isSortReversed = bookListSortState.value.isReversed
        SwitchOption(
            text = TEXT_REVERSE_ORDER,
            isChecked = isSortReversed,
            onChange = { switchIsSortReversed(bookListSortState, isSortReversed) }
        )
    }

}

private fun selectSort(
    sortState: MutableState<BookListSort>,
    sort: Sort
) {
    val newSort = sortState.value.copy(sortBy = sort)
    sortState.value = newSort
}

private fun switchIsSortReversed(
    sortState: MutableState<BookListSort>,
    isSortReversed: Boolean
) {
    val switchedSort = sortState.value.copy(isReversed = !isSortReversed)
    sortState.value = switchedSort
}