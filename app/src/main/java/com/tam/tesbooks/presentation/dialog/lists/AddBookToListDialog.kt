package com.tam.tesbooks.presentation.dialog.lists

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.presentation.dialog.BottomSheetDialog
import com.tam.tesbooks.presentation.dialog.BottomSheetDialogProvider
import com.tam.tesbooks.presentation.dialog.CheckboxOption
import com.tam.tesbooks.presentation.common.new_list.NewListControl
import com.tam.tesbooks.util.*

@Composable
fun AddBookToListDialog(
    isOpenState: MutableState<Boolean>,
    bookInfo: BookInfo,
    onChangeBookList: (bookInfo: BookInfo, bookList: BookList) -> Unit
) {
    if (!isOpenState.value) return

    val originalSavedInLists = bookInfo.savedInBookLists

    BottomSheetDialogProvider.showDialog(
        isOpenState = isOpenState,
        content = {

            AddBookToListDialogContent(
                isOpenState = isOpenState,
                savedInBookLists = originalSavedInLists,
                onSubmitLists = { newLists ->
                    resolveChangedBookLists(
                        bookInfo = bookInfo,
                        newLists = newLists,
                        originalLists = originalSavedInLists,
                        onChangeBookList = onChangeBookList
                    )
                }
            )

        }
    )

}

@Composable
private fun AddBookToListDialogContent(
    isOpenState: MutableState<Boolean>,
    savedInBookLists: List<BookList>,
    onSubmitLists: (newLists: List<BookList>) -> Unit,
    listsViewModel: ListsViewModel = hiltViewModel()
) {
    val savedInListsState = remember { mutableStateOf(savedInBookLists) }
    BottomSheetDialog(
        title = TEXT_ADD_TO_LISTS_DIALOG_TITLE,
        isOpenState = isOpenState,
        onSubmit = { onSubmitLists(savedInListsState.value) }
    ) {
        Spacer(modifier = Modifier.size(PADDING_SMALL))

        val bookLists = listsViewModel.state.bookLists
        bookLists.forEach { bookList ->
            val isListSelected = savedInListsState.value.contains(bookList)

            CheckboxOption(
                text = bookList.name,
                isChecked = isListSelected,
                onChange = {
                    selectBookList(
                        savedInBookListsState = savedInListsState,
                        bookList = bookList,
                        isListAlreadySelected = isListSelected
                    )
                },
                modifier = Modifier.padding(horizontal = PADDING_SMALL)
            )

            if (bookLists.last() != bookList) {
                Spacer(modifier = Modifier.size(PADDING_LARGE))
            }
        }

        NewListControl(
            isCreatingNewListState = remember { mutableStateOf(false) },
            newListInsidePadding = PaddingValues(start = PADDING_X_LARGE, top = PADDING_NORMAL, end = PADDING_X_LARGE, bottom = PADDING_SMALL),
            newListLeadingContent = { Spacer(Modifier.width(SIZE_ICON_NORMAL)) },
            modifier = Modifier.padding(start = PADDING_XX_SMALL, top = PADDING_SMALL)
        ) { newListName ->
            val createListEvent = ListsEvent.OnCreateNewList(newListName)
            listsViewModel.onEvent(createListEvent)
        }
    }
}

private fun selectBookList(
    savedInBookListsState: MutableState<List<BookList>>,
    bookList: BookList,
    isListAlreadySelected: Boolean
) {
    val originalBookLists = savedInBookListsState.value
    val newBookListsSelection =
        if (isListAlreadySelected)
            originalBookLists - bookList
        else
            originalBookLists + bookList
    savedInBookListsState.value = newBookListsSelection
}

private fun resolveChangedBookLists(
    bookInfo: BookInfo,
    newLists: List<BookList>,
    originalLists: List<BookList>,
    onChangeBookList: (bookInfo: BookInfo, bookList: BookList) -> Unit
) {
    val addedLists = newLists.filter { bookList ->
        !originalLists.contains(bookList)
    }
    val removedLists = originalLists.filter { bookList ->
        !newLists.contains(bookList)
    }
    val changedLists = addedLists + removedLists
    changedLists.forEach { bookList ->
        onChangeBookList(bookInfo, bookList)
    }
}
