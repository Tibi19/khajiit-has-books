package com.tam.tesbooks.presentation.common.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.tam.tesbooks.util.showError
import kotlinx.coroutines.flow.Flow

@Composable
fun OnErrorEffect(errorFlow: Flow<String>) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        errorFlow.collect { message ->
            showError(context, message)
        }
    }
}