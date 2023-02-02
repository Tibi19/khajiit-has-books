package com.tam.tesbooks.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = iceBlue,
    primaryVariant = iceBlueAccent,
    onSecondary = unfocusedIceBlue,
    secondary = gray,
    secondaryVariant = darkGray,
    onSurface = vibrantGray
)

private val DarkCustomColorPalette = CustomColors(
    onSurfaceVariant = shadowGray
)

private val LightColorPalette = lightColors(
    primary = paperYellow,
    primaryVariant = ashYellow,
    onSecondary = unfocusedPaperYellow,
    secondary = gray,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun KhajiitHasBooksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
//    val customColors = if (darkTheme) DarkCustomColorPalette else LightCustomColorPalette
    val colors = DarkColorPalette
    val customColors = DarkCustomColorPalette

    CustomColors.initialize(customColors)

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}