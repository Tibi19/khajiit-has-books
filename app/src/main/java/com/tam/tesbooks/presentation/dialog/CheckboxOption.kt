package com.tam.tesbooks.presentation.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.presentation.common.SectionText
import com.tam.tesbooks.ui.theme.CustomColors
import com.tam.tesbooks.util.CONTENT_CHECKBOX_OPTION_CHECKED_START
import com.tam.tesbooks.util.CONTENT_CHECKBOX_OPTION_UNCHECKED_START
import com.tam.tesbooks.util.PADDING_NORMAL
import com.tam.tesbooks.util.SIZE_ICON_NORMAL

@Composable
fun CheckboxOption(
    text: String,
    isChecked: Boolean,
    onChange: () -> Unit,
    modifier: Modifier = Modifier
) =
    Row(
        modifier = modifier.selectable(
            selected = isChecked,
            onClick = onChange
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(end = PADDING_NORMAL)
                .selectable(
                    selected = isChecked,
                    onClick = onChange
                )
        ) {
           if(isChecked) {
               Icon(
                   painter = painterResource(R.drawable.ic_checkbox_checked),
                   contentDescription = "$CONTENT_CHECKBOX_OPTION_CHECKED_START$text",
                   tint = MaterialTheme.colors.primaryVariant,
                   modifier = Modifier.size(SIZE_ICON_NORMAL)
               )
           } else {
               Icon(
                   painter = painterResource(R.drawable.ic_checkbox),
                   contentDescription = "$CONTENT_CHECKBOX_OPTION_UNCHECKED_START$text",
                   tint = CustomColors.colors.unfocusedOptionColor,
                   modifier = Modifier.size(SIZE_ICON_NORMAL)
               )
           }
        }
        SectionText(text = text)
    }