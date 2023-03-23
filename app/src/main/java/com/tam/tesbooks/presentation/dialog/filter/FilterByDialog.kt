package com.tam.tesbooks.presentation.dialog.filter

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.tam.tesbooks.R
import com.tam.tesbooks.domain.model.listing_modifier.LibraryFilter
import com.tam.tesbooks.presentation.dialog.BottomSheetDialog
import com.tam.tesbooks.presentation.dialog.BottomSheetDialogProvider
import com.tam.tesbooks.presentation.dialog.CheckboxOption
import com.tam.tesbooks.presentation.dialog.SwitchOption
import com.tam.tesbooks.util.*

@Composable
fun FilterByDialog(
    isOpenState: MutableState<Boolean>,
    libraryFilter: LibraryFilter,
    onSubmitFilter: (newLibraryFilter: LibraryFilter) -> Unit
) {
    if(!isOpenState.value) return

    BottomSheetDialogProvider.showDialog(
        isOpenState = isOpenState,
        content = {
            FilterByDialogContent(
                isOpenState = isOpenState,
                libraryFilter = libraryFilter,
                onSubmitFilter = { newFilter -> onSubmitFilter(newFilter) }
            )
        }
    )

}

@Composable
private fun FilterByDialogContent(
    isOpenState: MutableState<Boolean>,
    libraryFilter: LibraryFilter,
    onSubmitFilter: (newLibraryFilter: LibraryFilter) -> Unit,
    filterViewModel: FilterViewModel = hiltViewModel()
) {
    val filterState = remember { mutableStateOf(libraryFilter) }
    val options = filterViewModel.state.categories
    BottomSheetDialog(
        title = TEXT_FILTER_BY_DIALOG_TITLE,
        isOpenState = isOpenState,
        onSubmit = { onSubmitFilter(filterState.value) }
    ) {
        Spacer(modifier = Modifier.size(PADDING_SMALL))

        TagFilterWarning(newLibraryFilterState = filterState)

        options.forEach { category ->
            val isCategorySelected = filterState.value.categoryFilters.contains(category)
            CheckboxOption(
                text = category,
                isChecked = isCategorySelected,
                onChange = { selectCategory(filterState, isCategorySelected, category) },
                modifier = Modifier.padding(horizontal = PADDING_SMALL)
            )
            if (options.last() != category) {
                Spacer(modifier = Modifier.size(PADDING_LARGE))
            }
        }

        val isExcludingAnonymous = filterState.value.isExcludingAnonymous
        SwitchOption(
            text = TEXT_EXCLUDE_ANONYMOUS,
            isChecked = isExcludingAnonymous,
            onChange = { switchIsExcludingAnonymous(filterState, isExcludingAnonymous) },
            modifier = Modifier.padding(top = PADDING_SMALL)
        )
    }
}

@Composable
private fun TagFilterWarning(newLibraryFilterState: MutableState<LibraryFilter>) {
    val tagFilters = newLibraryFilterState.value.tagFilters
    if (tagFilters.isEmpty()) return

    val tagsGroupedString = remember { tagFilters.joinToString(TEXT_JOIN_TAGS_SEPARATOR) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = PADDING_SMALL)
            .padding(bottom = PADDING_NORMAL)
            .fillMaxWidth()
    ) {
        Text(
            text = "$TEXT_FILTER_TAG_WARNING$tagsGroupedString",
            style = MaterialTheme.typography.h5,
            modifier = Modifier.weight(weight = 1f, fill = true)
        )
        IconButton(onClick = { newLibraryFilterState.value = newLibraryFilterState.value.copy(tagFilters = emptyList()) }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_trash),
                contentDescription = CONTENT_CLEAR_TAG_FILTER,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.size(SIZE_ICON_NORMAL)
            )
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
