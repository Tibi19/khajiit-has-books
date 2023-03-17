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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.tam.tesbooks.R
import com.tam.tesbooks.presentation.reusable.DefaultTextButton
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
                        confirmationText = confirmationText,
                        declineText = declineText
                    ) { onSubmit() }
                }
            }

        }
    )

}

@Composable
fun DynamicDialog(
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
                    if (dialogTitle.isNotEmpty()) {
                        DialogTitle(title = dialogTitle)
                    }
                    dialogBody()
                    DialogButtons(
                        isDialogOpenState = isOpenState,
                        confirmationText = confirmationText,
                        declineText = declineText
                    ) { onSubmit() }
                }
            }

        },
        properties = DialogProperties(usePlatformDefaultWidth = false)
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
    confirmationText: String,
    declineText: String,
    onSubmit: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = PADDING_SMALL)
    ) {

        DefaultTextButton(
            iconPainter = painterResource(id = R.drawable.ic_cancel),
            iconContent = CONTENT_CANCEL_ACTION,
            text = declineText,
            modifier = Modifier.padding(end = PADDING_SMALL),
            iconModifier = Modifier
                .padding(end = PADDING_SMALL)
                .size(SIZE_DIALOG_CANCEL_ICON)
        ) {
            isDialogOpenState.value = false
        }

        DefaultTextButton(
            iconPainter = painterResource(id = R.drawable.ic_check),
            iconContent = CONTENT_CONFIRM_ACTION,
            text = confirmationText,
            iconModifier = Modifier
                .padding(end = PADDING_SMALL)
                .size(SIZE_ICON_X_SMALL)
        ) {
            onSubmit()
            isDialogOpenState.value = false
        }

    }
}