package com.tam.tesbooks.presentation.reusable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.tam.tesbooks.util.PADDING_SMALL

@Composable
fun LoadingMoreIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PADDING_SMALL),
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}