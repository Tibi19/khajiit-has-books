package com.tam.tesbooks.presentation.navigation.drawer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tam.tesbooks.presentation.common.NewNameField
import com.tam.tesbooks.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewListRow(
    isCreatingNewListState: MutableState<Boolean>,
    insidePadding: PaddingValues,
    leadingContent: @Composable () -> Unit,
    onNewListName: (String) -> Unit
) {
    if (!isCreatingNewListState.value) return

    val bringIntoViewOfNewListRequester = remember { BringIntoViewRequester() }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewOfNewListRequester)
            .padding(insidePadding)
    ) {
        leadingContent()

        NewNameField(
            isActiveState = isCreatingNewListState,
            confirmationContent = CONTENT_RENAME_LIST_DONE,
            cancellationContent = CONTENT_CREATE_LIST_CANCEL,
            bringParentIntoView = { bringIntoViewOfNewListRequester.bringIntoView() },
            onNewName = onNewListName
        )
    }
}