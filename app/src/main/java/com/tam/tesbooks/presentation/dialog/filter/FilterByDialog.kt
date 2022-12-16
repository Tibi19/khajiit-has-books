package com.tam.tesbooks.presentation.dialog.filter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowRow
import com.tam.tesbooks.domain.model.listing_modifier.LibraryFilter
import com.tam.tesbooks.presentation.dialog.DefaultDialog
import com.tam.tesbooks.util.*

@Preview
@Composable
fun FilterByDialog(
    isOpenState: MutableState<Boolean> = remember{ mutableStateOf(true) },
    libraryFilter: LibraryFilter = LibraryFilter(),
    onSubmitFilter: (newLibraryFilter: LibraryFilter) -> Unit = {}
) {
    if(!isOpenState.value) return

    val newLibraryFilterState = remember { mutableStateOf(libraryFilter) }
    DefaultDialog(
        isOpenState = isOpenState,
        onSubmit = { onSubmitFilter(newLibraryFilterState.value) },
        dialogTitle = TEXT_FILTER_BY_DIALOG_TITLE
    ) {
        FilterByDialogBody(newLibraryFilterState = newLibraryFilterState)
    }

}

@Composable
fun FilterByDialogBody(
    newLibraryFilterState: MutableState<LibraryFilter>,
    filterViewModel: FilterViewModel = hiltViewModel()
) {
    val radioOptions = filterViewModel.state.categories
    Column {
        TagFilterWarning(newLibraryFilterState = newLibraryFilterState)
        
        radioOptions.forEach { category ->

            val isCategorySelected = newLibraryFilterState.value.categoryFilters.contains(category)

            Row(
                modifier = Modifier.selectable(
                    selected = isCategorySelected,
                    onClick = { selectCategory(newLibraryFilterState, isCategorySelected, category) }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isCategorySelected,
                    onCheckedChange = { selectCategory(newLibraryFilterState, isCategorySelected, category) },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colors.primary
                    ),
                )
                Text(
                    text = category,
                    modifier = Dimens.PADDING_SMALL
                )
            }

        }

        val isExcludingAnonymous = newLibraryFilterState.value.isExcludingAnonymous
        Row(
            modifier = Modifier.selectable(
                selected = isExcludingAnonymous,
                onClick = { switchIsExcludingAnonymous(newLibraryFilterState, isExcludingAnonymous) }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = isExcludingAnonymous,
                onCheckedChange = { switchIsExcludingAnonymous(newLibraryFilterState, isExcludingAnonymous) },
                colors = SwitchDefaults.colors(
                    uncheckedThumbColor = MaterialTheme.colors.onSecondary,
                    checkedThumbColor = MaterialTheme.colors.primary,
                    checkedTrackColor = MaterialTheme.colors.primary
                )
            )
            Text(
                text = TEXT_EXCLUDE_ANONYMOUS,
                modifier = Dimens.PADDING_SMALL
            )
        }
    }
}

@Composable
private fun TagFilterWarning(newLibraryFilterState: MutableState<LibraryFilter>) {
    val tagFilters = newLibraryFilterState.value.tagFilters
    if (tagFilters.isNotEmpty()) {
        val tagsGroupedString = tagFilters.joinToString(" ")
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Dimens.PADDING_TAG_FILTER_WARNING.fillMaxWidth()
        ) {
            Text(
                text = "$TEXT_FILTER_TAG_WARNING$tagsGroupedString",
                modifier = Dimens.WIDTH_TAG_FILTER_WARNING_TEXT
            )
            IconButton(onClick = {
                newLibraryFilterState.value = newLibraryFilterState.value.copy(tagFilters = emptyList())
            }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = CONTENT_CLEAR_TAG_FILTER,
                    tint = MaterialTheme.colors.primary,
                    modifier = Dimens.SIZE_CANCEL_TAG_FILTER_ICON
                )
            }
        }
    }

}

private fun selectCategory(
    libraryFilterState: MutableState<LibraryFilter>,
    isCategorySelected: Boolean,
    category: String
) {
    val selectedCategories = libraryFilterState.value.categoryFilters.toMutableList()
    if(isCategorySelected) {
        selectedCategories.remove(category)
    } else {
        selectedCategories.add(category)
    }
    val newLibraryFilter = libraryFilterState.value.copy(categoryFilters = selectedCategories)
    libraryFilterState.value = newLibraryFilter
}

private fun switchIsExcludingAnonymous(
    libraryFilterState: MutableState<LibraryFilter>,
    isExcludingAnonymous: Boolean
) {
    val selectedLibraryFilter = libraryFilterState.value.copy(isExcludingAnonymous = !isExcludingAnonymous)
    libraryFilterState.value = selectedLibraryFilter
}