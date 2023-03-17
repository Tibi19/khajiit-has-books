package com.tam.tesbooks.presentation.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tam.tesbooks.util.PADDING_X_SMALL

@Composable
fun RadioOption(
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
) {
    Row(
        modifier = Modifier.selectable(
            selected = isSelected,
            onClick = onSelect
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colors.primary
            ),
            modifier = Modifier.padding(end = PADDING_X_SMALL)
        )
        Text(text = text)
    }
}