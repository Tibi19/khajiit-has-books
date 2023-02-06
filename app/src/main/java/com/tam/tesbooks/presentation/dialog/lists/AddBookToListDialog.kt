package com.tam.tesbooks.presentation.dialog.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.presentation.dialog.DynamicDialog
import com.tam.tesbooks.presentation.reusable.NewListButton
import com.tam.tesbooks.util.CONTENT_DELETE_LIST
import com.tam.tesbooks.util.Dimens
import com.tam.tesbooks.util.TEXT_ADD_TO_LISTS_DIALOG_TITLE

@Composable
fun AddBookToListDialog(
    isOpenState: MutableState<Boolean>,
    bookInfo: BookInfo,
    onChangeBookList: (bookInfo: BookInfo, bookList: BookList) -> Unit,
    listsViewModel: ListsViewModel = hiltViewModel()
) {
    if (!isOpenState.value) return

    val originalSavedInLists = bookInfo.savedInBookLists
    val newSavedInListsState = remember { mutableStateOf(originalSavedInLists) }
    DynamicDialog(
        isOpenState = isOpenState,
        onSubmit = {
            resolveChangedBookLists(
                bookInfo = bookInfo,
                newLists = newSavedInListsState.value,
                originalLists = originalSavedInLists,
                onChangeBookList = onChangeBookList
            )
        },
        dialogTitle = TEXT_ADD_TO_LISTS_DIALOG_TITLE
    ) {
        AddBookToListDialogBody(
            newSavedInListsState = newSavedInListsState,
            listsViewModel = listsViewModel
        )
    }
}

@Composable
fun AddBookToListDialogBody(
    newSavedInListsState: MutableState<List<BookList>>,
    listsViewModel: ListsViewModel
) {
    val bookLists = listsViewModel.state.bookLists
    Column {

        bookLists.forEach { bookList ->
            val isListSelected = newSavedInListsState.value.contains(bookList)
            val selectThisBookListLambda = {
                selectBookList(
                    newSavedInBookListsState = newSavedInListsState,
                    bookList = bookList,
                    isListAlreadySelected = isListSelected
                )
            }
            Row(
                modifier = Modifier.selectable(
                    selected = isListSelected,
                    onClick = selectThisBookListLambda
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isListSelected,
                    onCheckedChange = { selectThisBookListLambda() }
                )
                Text(text = bookList.name)
                Spacer(modifier = Modifier.weight(1f))

                if(!bookList.isDefault) {
                    val deleteListLambda = {
                        val deleteListEvent = ListsEvent.OnDeleteList(bookList)
                        listsViewModel.onEvent(deleteListEvent)
                    }
                    IconButton(onClick = deleteListLambda) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = CONTENT_DELETE_LIST,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        }

        NewListButton(
            isCreatingNewListState = remember { mutableStateOf(false) },
            modifier = Dimens.PADDING_START_NEW_LIST_IN_DIALOG
        )

    }
}

private fun selectBookList(
    newSavedInBookListsState: MutableState<List<BookList>>,
    bookList: BookList,
    isListAlreadySelected: Boolean
) {
    val originalBookLists = newSavedInBookListsState.value
    val newBookListsSelection =
        if (isListAlreadySelected)
            originalBookLists - bookList
        else
            originalBookLists + bookList
    newSavedInBookListsState.value = newBookListsSelection
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