package com.tam.tesbooks.presentation.dialog

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
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
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.presentation.common.SectionText
import com.tam.tesbooks.ui.theme.CustomColors
import com.tam.tesbooks.util.*

@Composable
fun SwitchOption(
    text: String,
    isChecked: Boolean,
    onChange: () -> Unit,
    modifier: Modifier = Modifier,
) =
    Row(
        modifier = modifier.selectable(
            selected = isChecked,
            onClick = onChange
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconToggleButton(
            checked = isChecked,
            onCheckedChange = { onChange() },
            modifier = Modifier.padding(end = PADDING_SWITCH_ICON_END)
        ) {
            val switchContentDescription = if (isChecked) "$CONTENT_SWITCH_OPTION_ON_START$text" else "$CONTENT_SWITCH_OPTION_OFF_START$text"
            val backgroundColor by animateColorAsState(if (isChecked) MaterialTheme.colors.primary else CustomColors.colors.disabledOptionColor)
            val thumbColor by animateColorAsState(if (isChecked) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.primary)
            val thumbAlignmentBias by animateFloatAsState(if (isChecked) 1f else -1f)
            val thumbDragState = rememberDraggableState { delta ->
                val isDraggingFromChecked = delta < 0 && isChecked
                val isDraggingFromUnchecked = delta > 0 && !isChecked
                if (isDraggingFromChecked || isDraggingFromUnchecked) {
                    onChange()
                }
            }
            Box(modifier = Modifier.size(SIZE_SWITCH_BOX)) {
                Icon(
                    painter = painterResource(R.drawable.ic_switch_background),
                    contentDescription = switchContentDescription,
                    tint = backgroundColor,
                    modifier = Modifier
                        .size(SIZE_ICON_LARGE)
                        .align(Alignment.Center)
                )
                Icon(
                    painter = painterResource(R.drawable.ic_thumb),
                    contentDescription = switchContentDescription,
                    tint = thumbColor,
                    modifier = Modifier
                        .size(SIZE_ICON_LARGE)
                        .align(BiasAlignment(horizontalBias = thumbAlignmentBias, verticalBias = 0f))
                        .draggable(orientation = Orientation.Horizontal, state = thumbDragState)
                )
            }
        }
        SectionText(text = text)
    }
