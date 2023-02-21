package com.tam.tesbooks.ui.theme

import androidx.compose.ui.graphics.Color

class CustomColors(
    val onSurfaceVariant: Color,
    val tagTextColor: Color
) {

    companion object {

        private var instance: CustomColors? = null
        val colors: CustomColors
            get() = instance ?: defaultColors

        private val defaultColors = CustomColors(
            onSurfaceVariant = Color.White,
            tagTextColor = Color.Black
        )

        fun initialize(customColors: CustomColors) {
            instance = customColors
        }

    }

}