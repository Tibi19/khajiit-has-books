package com.tam.tesbooks.presentation.reusable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import com.tam.tesbooks.util.PADDING_NORMAL
import com.tam.tesbooks.util.SIZE_ICON_NORMAL

@Composable
fun DefaultTextButton(
    iconPainter: Painter,
    iconContent: String?,
    text: String,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.button,
    iconModifier: Modifier = Modifier
        .padding(end = PADDING_NORMAL)
        .size(SIZE_ICON_NORMAL),
    onClick: () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Image(
            painter = iconPainter,
            contentDescription = iconContent,
            modifier = iconModifier
        )
        Text(
            text = text,
            style = textStyle
        )
    }
}