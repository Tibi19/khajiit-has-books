package com.tam.tesbooks.presentation.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import com.tam.tesbooks.util.Dimens
import com.tam.tesbooks.util.TEXT_CANCEL
import com.tam.tesbooks.util.TEXT_SUBMIT

@Composable
fun DefaultDialog(
    isOpenState: MutableState<Boolean>,
    onSubmit: () -> Unit,
    dialogTitle: String,
    dialogBody: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = { isOpenState.value = false },
        content = {

            Card(shape = RoundedCornerShape(Dimens.RADIUS_DEFAULT)) {
                Column(
                    modifier = Dimens.PADDING_DEFAULT
                        .width(IntrinsicSize.Max)
                        .verticalScroll(rememberScrollState())
                ) {
                    DialogTitle(title = dialogTitle)
                    dialogBody()
                    DialogButtons(isDialogOpenState = isOpenState) { onSubmit() }
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
    onSubmit: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(
            modifier = Dimens.PADDING_SMALL,
            onClick = { isDialogOpenState.value = false },
            shape = RoundedCornerShape(Dimens.RADIUS_DEFAULT)
        ) {
            Text(text = TEXT_CANCEL)
        }

        Button(
            modifier = Dimens.PADDING_SMALL,
            onClick = {
                onSubmit()
                isDialogOpenState.value = false
            },
            shape = RoundedCornerShape(Dimens.RADIUS_DEFAULT)
        ) {
            Text(text = TEXT_SUBMIT)
        }

    }
}