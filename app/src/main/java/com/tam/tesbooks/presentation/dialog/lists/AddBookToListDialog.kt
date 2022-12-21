package com.tam.tesbooks.presentation.dialog.lists

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.presentation.dialog.DynamicDialog
import com.tam.tesbooks.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

        val isCreatingNewListState = remember { mutableStateOf(false) }
        NewListNameField(
            isCreatingNewListState = isCreatingNewListState,
            sendNewListEvent = { newListEvent -> listsViewModel.onEvent(newListEvent) }
        )

        TextButton(
            onClick = {
                if (isCreatingNewListState.value) return@TextButton
                isCreatingNewListState.value = true
            },
            modifier = Dimens.PADDING_START_NEW_LIST_IN_DIALOG
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = CONTENT_CREATE_LIST,
                tint = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = TEXT_NEW_LIST)
        }

    }
}

@Composable
private fun NewListNameField(
    isCreatingNewListState: MutableState<Boolean>,
    sendNewListEvent: (ListsEvent.OnCreateNewList) -> Unit
) {
    if(!isCreatingNewListState.value) return

    val nameFieldFocusRequester = remember { FocusRequester() }
    var isNameFocusRequesterAttached by remember { mutableStateOf(false) }
    if(!isNameFocusRequesterAttached) {
        rememberCoroutineScope()
            .launch {
                while (!isNameFocusRequesterAttached) {
                    delay(TIME_WAIT_FOR_NEW_LIST_NAME_FOCUS_REQUESTER)
                }
                nameFieldFocusRequester.requestFocus()
            }
    }

    var newListName by remember { mutableStateOf("") }
    TextField(
        value = newListName,
        onValueChange = { newListName = it },
        trailingIcon = {
            if (newListName.isEmpty()) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = CONTENT_CREATE_LIST_CANCEL,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.clickable { isCreatingNewListState.value = false }
                )
                return@TextField
            }
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = CONTENT_CREATE_LIST_DONE,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.clickable {
                    val createListEvent = ListsEvent.OnCreateNewList(newListName)
                    sendNewListEvent(createListEvent)
                    isCreatingNewListState.value = false
                }
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        ),
        modifier = Dimens.PADDING_NEW_LIST_NAME_FIELD
            .width(Dimens.WIDTH_VALUE_NEW_LIST_NAME_FIELD)
            .focusRequester(nameFieldFocusRequester)
            .also { isNameFocusRequesterAttached = true }
    )
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