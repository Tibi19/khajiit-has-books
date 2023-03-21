package com.tam.tesbooks.ui.theme

import androidx.compose.ui.graphics.Color

class CustomColors(
    val onSurfaceVariant: Color,
    val tagTextColor: Color,
    val unfocusedOptionColor: Color,
) {

    companion object {

        private lateinit var instance: CustomColors
        val colors: CustomColors get() = instance

        fun initialize(customColors: CustomColors) {
            instance = customColors
        }

    }

}