package com.tam.tesbooks.presentation.reusable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.util.CONTENT_CREATE_LIST
import com.tam.tesbooks.util.TEXT_NEW_LIST

@Composable
fun NewListButton(
    isCreatingNewListState: MutableState<Boolean>,
    modifier: Modifier = Modifier
) {
    DefaultTextButton(
        iconPainter = painterResource(id = R.drawable.ic_add),
        iconContent = CONTENT_CREATE_LIST,
        text = TEXT_NEW_LIST,
        modifier = modifier
    ) {
        if (isCreatingNewListState.value) return@DefaultTextButton
        isCreatingNewListState.value = true
    }
}