package com.tam.tesbooks.presentation.screen.library

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.tam.tesbooks.util.*

@Preview(showBackground = true)
@Composable
fun LibrarySearchBar(libraryViewModel: LibraryViewModel = hiltViewModel()) {

    val state = libraryViewModel.state

    Row(
        modifier = Dimens.PADDING_BOTTOM_SMALL
            .fillMaxWidth()
            .background(MaterialTheme.colors.secondary),
        verticalAlignment = Alignment.CenterVertically
    ) {

        TextField(
            value = state.searchQuery,
            modifier = Dimens.PADDING_DEFAULT.weight(weight = 1.0f, fill = true),
            onValueChange = { libraryViewModel.onEvent(LibraryEvent.OnSearchQueryChange(it)) },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.textFieldColors(
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                textColor = Color.White,
                backgroundColor = MaterialTheme.colors.secondary
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = CONTENT_SEARCH,
                    tint = MaterialTheme.colors.primary
                )
            },
            shape = RoundedCornerShape(Dimens.RADIUS_DEFAULT),
            placeholder = { Text(text = TEXT_SEARCH, color = MaterialTheme.colors.onSecondary) }
        )

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.FilterAlt,
                contentDescription = CONTENT_FILTER_BOOKS,
                tint = MaterialTheme.colors.primary,
                modifier = Dimens.SIZE_SEARCH_BAR_ICON
            )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                imageVector = Icons.Default.Sort,
                contentDescription = CONTENT_ORDER_BOOKS,
                tint = MaterialTheme.colors.primary,
                modifier = Dimens.SIZE_SEARCH_BAR_ICON
            )
        }
    }
    
}