package com.tam.tesbooks.presentation.navigation.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.util.*

@Composable
fun BookmarksRow(goToBookmarksScreen: () -> Unit) =
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = PADDING_NORMAL)
            .background(color = MaterialTheme.colors.secondaryVariant)
            .clickable { goToBookmarksScreen() }
            .padding(vertical = PADDING_NORMAL, horizontal = PADDING_X_LARGE)
    ) {

        Image(
            painter = painterResource(R.drawable.ic_bookmarks_filled),
            contentDescription = CONTENT_BOOKMARKS,
            modifier = Modifier
                .padding(end = PADDING_NORMAL)
                .size(SIZE_ICON_NORMAL)
        )

        SectionText(text = TEXT_BOOKMARKS)

        Image(
            painter = painterResource(id = R.drawable.ic_forward),
            contentDescription = CONTENT_FORWARD_BOOKMARKS,
            modifier = Modifier
                .size(SIZE_ICON_NORMAL)
                .clickable { goToBookmarksScreen() }
        )

    }
