package com.tam.tesbooks.presentation.common

import android.view.ViewTreeObserver
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun isKeyboardOpenAsState(): State<Boolean> {
    val isKeyboardOpenState = remember { mutableStateOf(false) }
    val view = LocalView.current
    DisposableEffect(key1 = view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            isKeyboardOpenState.value = ViewCompat
                .getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime())
                ?: false
        }

        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    return isKeyboardOpenState
}