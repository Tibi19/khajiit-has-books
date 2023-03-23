package com.tam.tesbooks.presentation.dialog

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tam.tesbooks.presentation.common.DefaultPreview
import com.tam.tesbooks.util.*

@Composable
fun ConfirmationDialog(
    isOpenState: MutableState<Boolean>,
    onSubmit: () -> Unit,
    prompt: String
) {
    DefaultDialog(
        isOpenState = isOpenState,
        confirmationText = TEXT_CONFIRMATION_DIALOG_CONFIRM,
        declineText = TEXT_CONFIRMATION_DIALOG_DECLINE,
        onSubmit = onSubmit
    ) {
        Text(
            text = prompt,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(top = PADDING_SMALL)
        )
    }
}

private const val PREVIEW_PROMPT_LIST = "Favorite Dragon Stories"
@Preview(showBackground = true)
@Composable
fun ConfirmationDialogPreview() =
    DefaultPreview {
        ConfirmationDialog(
            isOpenState = remember { mutableStateOf(true) },
            onSubmit = {},
            prompt = "$TEXT_CONFIRM_LIST_DELETION_START$PREVIEW_PROMPT_LIST?"
        )
    }