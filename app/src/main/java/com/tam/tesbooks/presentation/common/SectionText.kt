package com.tam.tesbooks.presentation.common

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.tam.tesbooks.util.PADDING_NORMAL

@Composable
fun RowScope.SectionText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.h5,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    maxLines: Int = 1
) =
    Text(
        text = text,
        style = style,
        overflow = overflow,
        maxLines = maxLines,
        modifier = modifier
            .weight(1f)
            .padding(end = PADDING_NORMAL)
    )