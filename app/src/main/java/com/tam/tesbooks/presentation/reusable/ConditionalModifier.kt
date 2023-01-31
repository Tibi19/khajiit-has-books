package com.tam.tesbooks.presentation.reusable

import androidx.compose.ui.Modifier

fun Modifier.conditional(
    condition : Boolean,
    modifier : Modifier.() -> Modifier
) : Modifier =
    if (condition) {
        then(modifier(Modifier))
    } else {
        this
    }