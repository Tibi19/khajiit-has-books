package com.tam.tesbooks.presentation.reusable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.util.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun NewListButton(
    onNewList: (newListName: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val isCreatingNewListState = remember { mutableStateOf(false) }
    NewListNameField(
        isCreatingNewListState = isCreatingNewListState,
        onNewList = { newListName -> onNewList(newListName) },
        modifier = modifier
    )

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

@Composable
private fun NewListNameField(
    isCreatingNewListState: MutableState<Boolean>,
    onNewList: (newListName: String) -> Unit,
    modifier: Modifier = Modifier
) {
    if(!isCreatingNewListState.value) return

    val nameFieldFocusRequester = remember { FocusRequester() }
    var isNameFocusRequesterAttached by remember { mutableStateOf(false) }
    if(!isNameFocusRequesterAttached) {
        rememberCoroutineScope()
            .launch {
                while (!isNameFocusRequesterAttached) {
                    delay(TIME_WAIT_FOR_NEW_LIST_NAME_FOCUS_REQUESTER)
                }
                nameFieldFocusRequester.requestFocus()
            }
    }

    var newListName by remember { mutableStateOf("") }
    TextField(
        value = newListName,
        onValueChange = { newListName = it },
        trailingIcon = {
            if (newListName.isEmpty()) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = CONTENT_CREATE_LIST_CANCEL,
                    tint = MaterialTheme.colors.primary,
                    modifier = Modifier.clickable { isCreatingNewListState.value = false }
                )
                return@TextField
            }
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = CONTENT_CREATE_LIST_DONE,
                tint = MaterialTheme.colors.primary,
                modifier = Modifier.clickable {
                    onNewList(newListName)
                    isCreatingNewListState.value = false
                }
            )
        },
        singleLine = true,
        textStyle = MaterialTheme.typography.h5,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent
        ),
        modifier = modifier
            .padding(bottom = PADDING_NORMAL)
            .fillMaxWidth()
            .focusRequester(nameFieldFocusRequester)
            .also { isNameFocusRequesterAttached = true }
    )
}