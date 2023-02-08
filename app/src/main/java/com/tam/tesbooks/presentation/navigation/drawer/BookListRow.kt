package com.tam.tesbooks.presentation.navigation.drawer

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.tam.tesbooks.R
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.presentation.dialog.ConfirmationDialog
import com.tam.tesbooks.presentation.reusable.NewNameField
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookListRow(
    bookList: BookList,
    state: DrawerState,
    drawerViewModel: DrawerViewModel,
    isDrawerOpen: Boolean,
    goToListScreen: () -> Unit
) {
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { goToListScreen() }
            .bringIntoViewRequester(bringIntoViewRequester)
            .padding(vertical = PADDING_LARGE / 2)
            .padding(horizontal = PADDING_X_LARGE)
    ) {

        val listIconResource = when (bookList.name) {
            DEFAULT_BOOK_LIST_VIEWED -> R.drawable.ic_read_book_checked
            DEFAULT_BOOK_LIST_FAVORITE -> R.drawable.ic_favorite_filled
            DEFAULT_BOOK_LIST_READ_LATER -> R.drawable.ic_time
            else -> R.drawable.ic_list
        }

        val listIconContent = when (bookList.name) {
            DEFAULT_BOOK_LIST_VIEWED -> CONTENT_LIST_VIEWED
            DEFAULT_BOOK_LIST_FAVORITE -> CONTENT_LIST_FAVORITE
            DEFAULT_BOOK_LIST_READ_LATER -> CONTENT_LIST_READ_LATER
            else -> CONTENT_LIST
        }

        val isDeleteDialogOpenState = remember { mutableStateOf(false) }
        val isRenamingListState = remember { mutableStateOf(false) }

        if (!isDrawerOpen) {
            isRenamingListState.value = false
        }

        Image(
            painter = painterResource(listIconResource),
            contentDescription = listIconContent,
            modifier = Modifier
                .padding(end = PADDING_NORMAL)
                .size(SIZE_ICON_NORMAL)
        )

        if (isRenamingListState.value) {
            NewNameField(
                isActiveState = isRenamingListState,
                confirmationContent = CONTENT_RENAME_LIST_DONE,
                cancellationContent = CONTENT_RENAME_LIST_CANCEL,
                originalName = bookList.name,
                bringParentIntoView = { bringIntoViewRequester.bringIntoView() },
                onNewName = { newName ->
                    val renameEvent = DrawerEvent.OnRenameList(bookList, newName)
                    drawerViewModel.onEvent(renameEvent)
                }
            )
        } else {
            SectionText(text = bookList.name)
        }

        if (bookList.isDefault) {
            BooksInListCounter(state = state, bookList = bookList)
        } else {
            CustomListOptionsMenu(
                isDeleteDialogOpenState = isDeleteDialogOpenState,
                isRenamingListState = isRenamingListState,
            )
        }

        if (isDeleteDialogOpenState.value) {
            ConfirmationDialog(
                isOpenState = isDeleteDialogOpenState,
                onSubmit = {
                    val deleteListEvent = DrawerEvent.OnDeleteList(bookList)
                    drawerViewModel.onEvent(deleteListEvent)
                },
                prompt = "$TEXT_CONFIRM_LIST_DELETION_START${bookList.name}?"
            )
        }

    }
}

@Composable
private fun CustomListOptionsMenu(
    isDeleteDialogOpenState: MutableState<Boolean>,
    isRenamingListState: MutableState<Boolean>
) =
    Box {

        var isMenuExpanded by remember { mutableStateOf(false) }
        val menuItems: List<Pair<String, () -> Unit>> = listOf(
            Pair(TEXT_RENAME) { isRenamingListState.value = true },
            Pair(TEXT_DELETE) { isDeleteDialogOpenState.value = true }
        )

        if(!isRenamingListState.value) {
            // We only want 1 action icon - renaming the list already has its own action icon
            Image(
                painter = painterResource(id = R.drawable.ic_menu_dots),
                contentDescription = CONTENT_LIST_MENU,
                modifier = Modifier
                    .size(SIZE_ICON_NORMAL)
                    .clickable { isMenuExpanded = !isMenuExpanded }
            )
        }

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            offset = DpOffset(0.dp, PADDING_X_SMALL),
            modifier = Modifier.background(MaterialTheme.colors.secondaryVariant)
        ) {
            menuItems.forEach { menuItem ->
                DropdownMenuItem(onClick = {
                    menuItem.second()
                    isMenuExpanded = false
                }) {
                    Text(
                        text = menuItem.first,
                        style = MaterialTheme.typography.h5
                    )
                }
            }
        }

    }

@Composable
private fun BooksInListCounter(
    state: DrawerState,
    bookList: BookList
) {
    val booksInListCount = state.defaultListNameToBookCountMap[bookList.name]
    Text(
        text = booksInListCount.toString(),
        style = MaterialTheme.typography.h5,
        modifier = Modifier.padding(end = PADDING_BOOKS_IN_LIST_COUNT_END)
    )
}