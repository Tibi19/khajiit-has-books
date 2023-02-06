package com.tam.tesbooks.presentation.navigation.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.tam.tesbooks.presentation.reusable.NewListButton
import com.tam.tesbooks.presentation.reusable.OnErrorEffect
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
    val isCreatingNewListState = remember { mutableStateOf(false) }

    OnErrorEffect(errorFlow = drawerViewModel.errorFlow)

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

        if(isCreatingNewListState.value) {
            NewListRow(isCreatingNewListState = isCreatingNewListState) { newListName ->
                val createListEvent = DrawerEvent.OnCreateNewList(newListName)
                drawerViewModel.onEvent(createListEvent)
            }
        }

        NewListButton(
            isCreatingNewListState = isCreatingNewListState,
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
