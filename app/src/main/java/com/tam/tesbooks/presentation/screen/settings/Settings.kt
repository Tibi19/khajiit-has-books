package com.tam.tesbooks.presentation.screen.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tam.tesbooks.util.PADDING_LARGE
import com.tam.tesbooks.util.TEXT_TO_BE_ADDED

@Composable
fun Settings() {
    Text(
        text = TEXT_TO_BE_ADDED,
        style = MaterialTheme.typography.h4,
        modifier = Modifier.padding(PADDING_LARGE)
    )
}