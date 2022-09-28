package com.tam.tesbooks.util

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Dimens {
    val PADDING_DEFAULT = Modifier.padding(10.dp)
    val PADDING_SMALL = Modifier.padding(5.dp)
    val PADDING_LARGE = Modifier.padding(15.dp)
    val PADDING_HORIZONTAL_SMALL = Modifier.padding(horizontal = 5.dp)
    val PADDING_BOTTOM = Modifier.padding(bottom = 10.dp)
    val PADDING_BOTTOM_SMALL = Modifier.padding(bottom = 5.dp)
    val PADDING_TEXT = Modifier.padding(vertical = 5.dp, horizontal = 15.dp)
    val PADDING_LAST_TEXT = Modifier.padding(top = 5.dp, bottom = 10.dp, start = 15.dp, end = 15.dp)
    val RADIUS_DEFAULT = 15.dp
    val ELEVATION_DEFAULT = 10.dp
    val SIZE_BOTTOM_BAR_ICON = Modifier.size(30.dp)
    val SIZE_SEARCH_BAR_ICON = Modifier.size(25.dp)
    val SIZE_BOTTOM_BAR_HEIGHT = Modifier.height(56.dp)
    val SPACING_TEXT_DEFAULT = 22.sp
}