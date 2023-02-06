package com.tam.tesbooks.presentation.navigation.drawer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.presentation.reusable.NewNameField
import com.tam.tesbooks.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NewListRow(
    isCreatingNewListState: MutableState<Boolean>,
    onNewListName: (String) -> Unit
) {
    val bringIntoViewOfNewListRequester = remember { BringIntoViewRequester() }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .bringIntoViewRequester(bringIntoViewOfNewListRequester)
            .padding(vertical = PADDING_LARGE / 2)
            .padding(horizontal = PADDING_X_LARGE)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_list),
            contentDescription = CONTENT_LIST,
            modifier = Modifier
                .padding(end = PADDING_NORMAL)
                .size(SIZE_ICON_NORMAL)
        )

        NewNameField(
            isActiveState = isCreatingNewListState,
            confirmationContent = CONTENT_RENAME_LIST_DONE,
            cancellationContent = CONTENT_CREATE_LIST_CANCEL,
            bringParentIntoView = { bringIntoViewOfNewListRequester.bringIntoView() },
            onNewName = onNewListName
        )
    }
}