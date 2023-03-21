package com.tam.tesbooks.presentation.dialog

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.ui.theme.CustomColors
import com.tam.tesbooks.util.CONTENT_RADIO_OPTION_START
import com.tam.tesbooks.util.PADDING_SMALL
import com.tam.tesbooks.util.SCALE_RADIO_OPTION_CHECKED_INSIDE
import com.tam.tesbooks.util.SIZE_ICON_LARGE

@Composable
fun RadioOption(
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.selectable(
            selected = isSelected,
            onClick = onSelect
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val radioButtonDescription = "$CONTENT_RADIO_OPTION_START$text"
        val checkedInsideScale by animateFloatAsState(if (isSelected) SCALE_RADIO_OPTION_CHECKED_INSIDE else 0f)
        IconToggleButton(
            checked = isSelected,
            onCheckedChange = { onSelect() },
            modifier = Modifier.padding(end = PADDING_SMALL)
        ) {
            if(isSelected) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(R.drawable.ic_radio),
                        contentDescription = radioButtonDescription,
                        tint = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier.size(SIZE_ICON_LARGE)
                    )
                    Icon(
                        painter = painterResource(R.drawable.ic_radio_checked_inside),
                        contentDescription = radioButtonDescription,
                        tint = MaterialTheme.colors.primaryVariant,
                        modifier = Modifier
                            .size(SIZE_ICON_LARGE)
                            .scale(checkedInsideScale)
                    )
                }
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_radio),
                    contentDescription = radioButtonDescription,
                    tint = CustomColors.colors.unfocusedOptionColor,
                    modifier = Modifier.size(SIZE_ICON_LARGE)
                )
            }
        }
        SectionText(text = text)
    }
}