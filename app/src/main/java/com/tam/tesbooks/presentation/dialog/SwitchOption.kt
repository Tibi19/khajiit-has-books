package com.tam.tesbooks.presentation.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.util.Dimens
import com.tam.tesbooks.util.PADDING_SMALL
import com.tam.tesbooks.util.PADDING_X_SMALL
import com.tam.tesbooks.util.TEXT_REVERSE_ORDER

@Composable
fun SwitchOption(
    text: String,
    isChecked: Boolean,
    onChange: () -> Unit,
) =
    Row(
        modifier = Modifier.selectable(
            selected = isChecked,
            onClick = onChange
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Switch(
            checked = isChecked,
            onCheckedChange = { onChange() },
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = MaterialTheme.colors.onSecondary,
                checkedThumbColor = MaterialTheme.colors.primary,
                checkedTrackColor = MaterialTheme.colors.primary
            ),
            modifier = Modifier.padding(end = PADDING_SMALL)
        )
        SectionText(text = text)
    }