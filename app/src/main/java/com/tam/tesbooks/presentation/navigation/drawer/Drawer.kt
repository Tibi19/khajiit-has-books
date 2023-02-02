package com.tam.tesbooks.presentation.navigation.drawer

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tam.tesbooks.R
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.presentation.reusable.NewListButton
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.ui.theme.CustomColors
import com.tam.tesbooks.ui.theme.KhajiitHasBooksTheme
import com.tam.tesbooks.util.*

@Composable
fun Drawer(
    scaffoldState: ScaffoldState,
    navController: NavHostController,
    drawerViewModel: DrawerViewModel = hiltViewModel()
) {

    val state = drawerViewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        Image(
            painter = painterResource(id = R.drawable.tesbooks_banner),
            contentDescription = CONTENT_BANNER,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = PADDING_X_LARGE, horizontal = PADDING_XX_LARGE)
        )
        
        BookmarksRow(goToBookmarksScreen = {})

        Text(
            text = TEXT_BOOK_LISTS,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            color = CustomColors.colors.onSurfaceVariant,
            modifier = Modifier.padding(start = PADDING_X_LARGE, bottom = PADDING_LARGE / 2)
        )

        state.bookLists.forEach { bookList ->
            BookListRow(
                bookList = bookList,
                state = state,
                drawerViewModel = drawerViewModel,
                goToListScreen = {}
            )
        }

        NewListButton(
            onNewList = { newListName ->
                val createListEvent = DrawerEvent.OnCreateNewList(newListName)
                drawerViewModel.onEvent(createListEvent)
            },
            modifier = Modifier.padding(horizontal = PADDING_NEW_LIST_IN_DRAWER)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = TEXT_DISCLAIMER,
            color = MaterialTheme.colors.onSurface,
            fontSize = SIZE_TEXT_DISCLAIMER,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = PADDING_DISCLAIMER_TOP, bottom = PADDING_LARGE)
                .padding(horizontal = PADDING_DISCLAIMER_HORIZONTAL)
                .fillMaxWidth()
        )

    }

}

@Composable
private fun BookmarksRow(goToBookmarksScreen: () -> Unit) =
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = PADDING_NORMAL)
            .background(color = MaterialTheme.colors.secondaryVariant)
            .clickable { goToBookmarksScreen() }
            .padding(vertical = PADDING_NORMAL, horizontal = PADDING_X_LARGE)
    ) {

        Image(
            painter = painterResource(R.drawable.ic_bookmarks_filled),
            contentDescription = CONTENT_BOOKMARKS,
            modifier = Modifier
                .padding(end = PADDING_NORMAL)
                .size(SIZE_ICON_NORMAL)
        )

        SectionText(text = TEXT_BOOKMARKS)

        Image(
            painter = painterResource(id = R.drawable.ic_forward),
            contentDescription = CONTENT_FORWARD_BOOKMARKS,
            modifier = Modifier
                .size(SIZE_ICON_NORMAL)
                .clickable { goToBookmarksScreen() }
        )

    }

@Composable
private fun BookListRow(
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

        val listIconResource = when(bookList.name) {
            DEFAULT_BOOK_LIST_VIEWED -> R.drawable.ic_read_book_checked
            DEFAULT_BOOK_LIST_FAVORITE -> R.drawable.ic_favorite_filled
            DEFAULT_BOOK_LIST_READ_LATER -> R.drawable.ic_time
            else -> R.drawable.ic_list
        }

        val listIconContent = when(bookList.name) {
            DEFAULT_BOOK_LIST_VIEWED -> CONTENT_LIST_VIEWED
            DEFAULT_BOOK_LIST_FAVORITE -> CONTENT_LIST_FAVORITE
            DEFAULT_BOOK_LIST_READ_LATER -> CONTENT_LIST_READ_LATER
            else -> CONTENT_LIST
        }

        Image(
            painter = painterResource(listIconResource),
            contentDescription = listIconContent,
            modifier = Modifier
                .padding(end = PADDING_NORMAL)
                .size(SIZE_ICON_NORMAL)
        )

        SectionText(text = bookList.name)

        if(bookList.isDefault) {
            val booksInListCount = state.defaultListNameToBookCountMap[bookList.name]
            Text(
                text = booksInListCount.toString(),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(end = PADDING_BOOKS_IN_LIST_COUNT_END)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_menu_dots),
                contentDescription = CONTENT_LIST_MENU,
                modifier = Modifier
                    .size(SIZE_ICON_NORMAL)
                    .clickable { }
            )
        }

    }

@Preview
@Composable
fun DrawerPreview() =
    KhajiitHasBooksTheme {
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(350.dp),
            color = MaterialTheme.colors.secondary,
            contentColor = Color.White
        ) {
            Drawer(
                scaffoldState = rememberScaffoldState(),
                navController = rememberNavController()
            )
        }
    }
