package com.tam.tesbooks.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.tam.tesbooks.R
import com.tam.tesbooks.util.SIZE_TEXT_NORMAL
import com.tam.tesbooks.util.SIZE_TEXT_SMALL

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
    body1 = TextStyle(fontSize = SIZE_TEXT_SMALL),
    h5 = TextStyle(fontSize = SIZE_TEXT_NORMAL),
    button = TextStyle(
        fontSize = SIZE_TEXT_NORMAL,
        fontWeight = FontWeight.SemiBold,
    )
)