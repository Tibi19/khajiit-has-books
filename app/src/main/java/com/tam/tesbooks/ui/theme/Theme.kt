package com.tam.tesbooks.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = iceBlue,
    primaryVariant = iceBlueAccent,
    onSecondary = unfocusedIceBlue,
    secondary = gray,
    secondaryVariant = darkGray,
    onSurface = vibrantGray,
    onPrimary = Color.Black
)

private val DarkCustomColorPalette = CustomColors(
    onSurfaceVariant = shadowGray,
    tagTextColor = Color.Black,
    unfocusedOptionColor = unfocusedGray,
    disabledOptionColor = disabledGray
)

private val LightColorPalette = lightColors(
    primary = paperYellow,
    primaryVariant = ashYellow,
    onSecondary = unfocusedPaperYellow,
    secondary = gray,
)

@Composable
fun KhajiitHasBooksTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
//    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
//    val customColors = if (darkTheme) DarkCustomColorPalette else LightCustomColorPalette
    val colors = DarkColorPalette
    val customColors = DarkCustomColorPalette

    systemUiController.setStatusBarColor(Color.Black) // if (darkTheme) Color.Black else ...

    CustomColors.initialize(customColors)

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

}