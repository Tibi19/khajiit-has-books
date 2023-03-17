package com.tam.tesbooks.presentation.reusable

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.tam.tesbooks.R
import com.tam.tesbooks.util.*

@Composable
fun DialogControl(
    confirmText: String,
    cancelText: String,
    textStyle: TextStyle = MaterialTheme.typography.button,
    onCancel: () -> Unit,
    onSubmit: () -> Unit
) {
    DefaultTextButton(
        iconPainter = painterResource(id = R.drawable.ic_cancel),
        iconContent = CONTENT_CANCEL_ACTION,
        text = cancelText,
        modifier = Modifier.padding(end = PADDING_SMALL),
        textStyle = textStyle,
        iconModifier = Modifier
            .padding(end = PADDING_SMALL)
            .size(SIZE_DIALOG_CANCEL_ICON),
        onClick = onCancel
    )

    DefaultTextButton(
        iconPainter = painterResource(id = R.drawable.ic_check),
        iconContent = CONTENT_CONFIRM_ACTION,
        text = confirmText,
        textStyle = textStyle,
        iconModifier = Modifier
            .padding(end = PADDING_SMALL)
            .size(SIZE_ICON_X_SMALL),
        onClick = onSubmit
    )
}