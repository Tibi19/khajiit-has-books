package com.tam.tesbooks.presentation.navigation.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.tam.tesbooks.R
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.presentation.dialog.ConfirmationDialog
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.util.*

@Composable
fun BookListRow(
    bookList: BookList,
    state: DrawerState,
    drawerViewModel: DrawerViewModel,
    goToListScreen: () -> Unit
) =
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { goToListScreen() }
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

        val isDeleteListDialogOpenState = remember { mutableStateOf(false) }

        Image(
            painter = painterResource(listIconResource),
            contentDescription = listIconContent,
            modifier = Modifier
                .padding(end = PADDING_NORMAL)
                .size(SIZE_ICON_NORMAL)
        )

        SectionText(text = bookList.name)

        if (bookList.isDefault) {
            BooksInListCounter(state = state, bookList = bookList)
        } else {
            CustomListOptionsMenu(isDeleteListDialogOpenState = isDeleteListDialogOpenState)
        }

        if (isDeleteListDialogOpenState.value) {
            ConfirmationDialog(
                isOpenState = isDeleteListDialogOpenState,
                onSubmit = {
                    val deleteListEvent = DrawerEvent.OnDeleteList(bookList)
                    drawerViewModel.onEvent(deleteListEvent)
                },
                prompt = "$TEXT_CONFIRM_LIST_DELETION_START${bookList.name}?"
            )
        }

    }

@Composable
private fun CustomListOptionsMenu(isDeleteListDialogOpenState: MutableState<Boolean>) =
    Box {

        var isMenuExpanded by remember { mutableStateOf(false) }
        val menuItems: List<Pair<String, () -> Unit>> = listOf(
            Pair(TEXT_RENAME) {},
            Pair(TEXT_DELETE) {
                isDeleteListDialogOpenState.value = true
                isMenuExpanded = false
            }
        )

        Image(
            painter = painterResource(id = R.drawable.ic_menu_dots),
            contentDescription = CONTENT_LIST_MENU,
            modifier = Modifier
                .size(SIZE_ICON_NORMAL)
                .clickable { isMenuExpanded = !isMenuExpanded }
        )

        DropdownMenu(
            expanded = isMenuExpanded,
            onDismissRequest = { isMenuExpanded = false },
            offset = DpOffset(0.dp, PADDING_X_SMALL),
            modifier = Modifier.background(MaterialTheme.colors.secondaryVariant)
        ) {
            menuItems.forEach { menuItem ->
                DropdownMenuItem(onClick = menuItem.second) {
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