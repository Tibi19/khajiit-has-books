package com.tam.tesbooks.presentation.common

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tam.tesbooks.ui.theme.KhajiitHasBooksTheme

@Composable
fun DefaultPreview(content: @Composable () -> Unit) =
    KhajiitHasBooksTheme {
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(350.dp),
            color = MaterialTheme.colors.secondary,
            contentColor = Color.White
        ) {
            content()
        }
    }