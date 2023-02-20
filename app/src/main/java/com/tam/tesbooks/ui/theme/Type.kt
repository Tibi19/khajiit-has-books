package com.tam.tesbooks.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.tam.tesbooks.R
import com.tam.tesbooks.util.*

val SourceSansProFamily = FontFamily(
    Font(R.font.source_sans_pro, FontWeight.Normal),
    Font(R.font.source_sans_pro_bold, FontWeight.Bold),
    Font(R.font.source_sans_pro_semibold, FontWeight.SemiBold)
)

val EBGaramondFontFamily = FontFamily(
    Font(R.font.eb_garamond, FontWeight.Normal),
    Font(R.font.eb_garamond_bold, FontWeight.Bold)
)

val Typography = Typography(
    defaultFontFamily = SourceSansProFamily,
    body1 = TextStyle(fontSize = SIZE_TEXT_X_SMALL),
    h2 = TextStyle(
        fontFamily = EBGaramondFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = SIZE_TEXT_LARGE,
        letterSpacing = SPACING_H2_LETTERS
    ),
    h4 = TextStyle(fontSize = SIZE_TEXT_LARGE),
    h5 = TextStyle(fontSize = SIZE_TEXT_NORMAL),
    h6 = TextStyle(
        fontSize = SIZE_TEXT_SMALL,
        fontWeight = FontWeight.Bold
    ),
    button = TextStyle(
        fontSize = SIZE_TEXT_NORMAL,
        fontWeight = FontWeight.SemiBold,
    )
)