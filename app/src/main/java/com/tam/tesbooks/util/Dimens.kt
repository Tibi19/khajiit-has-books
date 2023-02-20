package com.tam.tesbooks.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val HIGHLIGHT_ALPHA_BOOKMARK = 0.15f
const val THRESHOLD_DISMISS_BOOKMARK = 0.2f
const val SCALE_DISMISSED_BOOKMARK_BACKGROUND = 1f
const val SCALE_DISMISSING_BOOKMARK_BACKGROUND = 0.9f
val PADDING_XX_SMALL = 2.5.dp
val PADDING_X_SMALL = 5.dp
val PADDING_SMALL = 10.dp
val PADDING_NORMAL = 18.dp
val PADDING_LARGE = 24.dp
val PADDING_X_LARGE = 30.dp
val PADDING_XX_LARGE = 50.dp
val SIZE_ICON_NORMAL = 23.dp
val SIZE_ICON_SMALL = 16.dp
val SIZE_TEXT_X_SMALL = 15.sp
val SIZE_TEXT_SMALL = 16.sp
val SIZE_TEXT_NORMAL = 18.sp
val SIZE_TEXT_LARGE = 20.sp
val PADDING_BOOKS_IN_LIST_COUNT_END = 8.dp
val PADDING_NEW_LIST_IN_DRAWER = 22.dp
val PADDING_DISCLAIMER_TOP = 65.dp
val PADDING_DISCLAIMER_HORIZONTAL = 40.dp
val SIZE_TEXT_DISCLAIMER = 11.sp
val SIZE_DIALOG_CANCEL_ICON = 13.dp
val OFFSET_NEW_NAME_INDICATOR = 8.dp
val SIZE_NEW_NAME_INDICATOR = 2.dp
val SIZE_BOOKMARKS_DIVIDER = 1.dp
val SIZE_ICON_MARK_VIEWED_BOOK = 20.dp
val PADDING_TOP_ICON_MARK_VIEWED_BOOK = 3.dp
val SPACING_H2_LETTERS = 0.9.sp
val PADDING_BOTTOM_BOOK_DETAILS_TEXT = 8.dp
val SPACING_TAGS_FLOW_ROW = 5.dp
val SIZE_TAG_TEXT = 12.sp

object Dimens {
    val PADDING_DEFAULT = Modifier.padding(10.dp)
    val PADDING_SMALL = Modifier.padding(5.dp)
    val PADDING_LARGE = Modifier.padding(15.dp)
    val PADDING_HORIZONTAL_SMALL = Modifier.padding(horizontal = 5.dp)
    val PADDING_BOTTOM = Modifier.padding(bottom = 10.dp)
    val PADDING_BOTTOM_SMALL = Modifier.padding(bottom = 5.dp)
    val PADDING_TEXT_OLD = Modifier.padding(vertical = 5.dp, horizontal = 15.dp)
    val PADDING_LAST_TEXT = Modifier.padding(top = 5.dp, bottom = 10.dp, start = 15.dp, end = 15.dp)
    val RADIUS_DEFAULT = 15.dp
    val ELEVATION_DEFAULT = 10.dp
    val SIZE_BOTTOM_BAR_ICON = Modifier.size(30.dp)
    val SIZE_SEARCH_BAR_ICON = Modifier.size(25.dp)
    val SIZE_BOTTOM_BAR_HEIGHT = Modifier.height(56.dp)
    val SPACING_TEXT_DEFAULT = 22.sp
    val WIDTH_TAG_FILTER_WARNING_TEXT = Modifier.fillMaxWidth(0.85f)
    val SIZE_CANCEL_TAG_FILTER_ICON = Modifier.size(25.dp)
    val PADDING_TAG_FILTER_WARNING = Modifier.padding(top = 5.dp, start = 15.dp, end = 10.dp)
    val SIZE_BOOK_BAR_ICON = Modifier.size(25.dp)
    val STROKE_WIDTH_OUTLINED_BUTTON = 1.dp
    val PADDING_START_NEW_LIST_IN_DIALOG = Modifier.padding(start = 4.dp)
    val PADDING_NEW_LIST_NAME_FIELD = Modifier.padding(start = 17.dp, end = 10.dp, bottom = 5.dp)
    val WIDTH_VALUE_NEW_LIST_NAME_FIELD = 175.dp
}