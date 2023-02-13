package com.tam.tesbooks.presentation.reusable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.util.CONTENT_FORWARD_BOOKMARKS
import com.tam.tesbooks.util.PADDING_NORMAL
import com.tam.tesbooks.util.SIZE_ICON_NORMAL

@Composable
fun BarRow(
    modifier: Modifier = Modifier,
    paddingInside: PaddingValues = PaddingValues(PADDING_NORMAL),
    backgroundColor: Color = MaterialTheme.colors.secondaryVariant,
    onOptionalBackIcon: (() -> Unit)? = null,
    onOptionalForwardIcon: (() -> Unit)? = null,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .padding(paddingInside),
        verticalAlignment = Alignment.CenterVertically
    ) {
        onOptionalBackIcon?.let { onBackIcon ->
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = CONTENT_FORWARD_BOOKMARKS,
                modifier = Modifier
                    .padding(end = PADDING_NORMAL)
                    .size(SIZE_ICON_NORMAL)
                    .clickable { onBackIcon() }
            )
        }

        content()

        onOptionalForwardIcon?.let { onForwardIcon ->
            Image(
                painter = painterResource(id = R.drawable.ic_forward),
                contentDescription = CONTENT_FORWARD_BOOKMARKS,
                modifier = Modifier
                    .size(SIZE_ICON_NORMAL)
                    .clickable { onForwardIcon() }
            )
        }
    }
}