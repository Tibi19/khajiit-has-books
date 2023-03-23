package com.tam.tesbooks.presentation.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tam.tesbooks.util.*

@Composable
fun DefaultDialog(
    isOpenState: MutableState<Boolean>,
    onSubmit: () -> Unit,
    dialogTitle: String = "",
    confirmationText: String = TEXT_SUBMIT,
    declineText: String = TEXT_CANCEL,
    dialogBody: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = { isOpenState.value = false },
        content = {

            Card {
                Column(
                    modifier = Modifier
                        .padding(horizontal = PADDING_LARGE, vertical = PADDING_SMALL)
                        .width(IntrinsicSize.Max)
                        .verticalScroll(rememberScrollState())
                ) {
                    if(dialogTitle.isNotEmpty()) {
                        DialogTitle(title = dialogTitle)
                    }
                    dialogBody()
                    DialogButtons(
                        isDialogOpenState = isOpenState,
                        confirmText = confirmationText,
                        cancelText = declineText
                    ) { onSubmit() }
                }
            }

        }
    )

}

@Composable
fun DialogTitle(title: String) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        modifier = Dimens.PADDING_DEFAULT.fillMaxWidth()
    )
}

@Composable
fun DialogButtons(
    isDialogOpenState: MutableState<Boolean>,
    confirmText: String,
    cancelText: String,
    onSubmit: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = PADDING_SMALL)
    ) {
        DialogControl(
            confirmText = confirmText,
            cancelText = cancelText,
            onCancel = { isDialogOpenState.value = false },
            onSubmit = {
                onSubmit()
                isDialogOpenState.value = false
            }
        )
    }
}