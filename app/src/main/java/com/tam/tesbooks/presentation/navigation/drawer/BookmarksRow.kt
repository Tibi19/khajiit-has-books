package com.tam.tesbooks.presentation.navigation.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.presentation.reusable.BarRow
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.util.*

@Composable
fun BookmarksRow(goToBookmarksScreen: () -> Unit) =
    BarRow(
        onOptionalForwardIcon = goToBookmarksScreen,
        forwardIconContentDescription = CONTENT_FORWARD_BOOKMARKS,
        paddingInside = PaddingValues(vertical = PADDING_NORMAL, horizontal = PADDING_X_LARGE),
        modifier = Modifier.clickable { goToBookmarksScreen() }
    ) {
        Image(
            painter = painterResource(R.drawable.ic_bookmarks_filled),
            contentDescription = CONTENT_BOOKMARKS,
            modifier = Modifier
                .padding(end = PADDING_NORMAL)
                .size(SIZE_ICON_NORMAL)
        )

        SectionText(text = TEXT_BOOKMARKS)
    }
