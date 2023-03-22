package com.tam.tesbooks.presentation.screen.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.tam.tesbooks.presentation.dialog.OrderByDialog
import com.tam.tesbooks.presentation.dialog.filter.FilterByDialog
import com.tam.tesbooks.util.*
import com.tam.tesbooks.R

@Preview(showBackground = true)
@Composable
fun LibrarySearchBar(libraryViewModel: LibraryViewModel = hiltViewModel()) {

    val state = libraryViewModel.state
    val isOrderByDialogOpen = remember { mutableStateOf(false) }
    val isFilterByDialogOpen = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondary)
            .padding(horizontal = PADDING_SMALL, vertical = PADDING_X_SMALL),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            value = state.searchQuery,
            onValueChange = { libraryViewModel.onEvent(LibraryEvent.OnSearchQueryChange(it)) },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                textColor = Color.White,
                backgroundColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = CONTENT_SEARCH,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.size(SIZE_ICON_SMALL)
                )
            },
            placeholder = {
                Text(
                    text = TEXT_SEARCH,
                    color = MaterialTheme.colors.onSecondary,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.SemiBold
                )
            },
            textStyle = MaterialTheme.typography.h5,
            modifier = Modifier.weight(weight = 1f, fill = true),
        )

        IconButton(onClick = { isFilterByDialogOpen.value = true }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_filter),
                contentDescription = CONTENT_FILTER_BOOKS,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(SIZE_ICON_SMALL)
            )
        }

        IconButton(onClick = { isOrderByDialogOpen.value = true }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_order),
                contentDescription = CONTENT_ORDER_BOOKS,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(SIZE_ICON_ORDER_BY)
            )
        }
    }

    OrderByDialog(
        isOpenState = isOrderByDialogOpen,
        libraryOrder = state.libraryOrder,
        onSubmitOrder = { newLibraryOrder ->
            val newOrderEvent = LibraryEvent.OnOrderChange(newLibraryOrder)
            libraryViewModel.onEvent(newOrderEvent)
        }
    )

    FilterByDialog(
        isOpenState = isFilterByDialogOpen,
        libraryFilter = state.libraryFilter,
        onSubmitFilter = { newLibraryFilter ->
            val newFilterEvent = LibraryEvent.OnFilterChange(newLibraryFilter)
            libraryViewModel.onEvent(newFilterEvent)
        }
    )

}