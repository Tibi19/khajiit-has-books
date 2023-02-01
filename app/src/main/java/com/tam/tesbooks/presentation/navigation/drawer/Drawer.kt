package com.tam.tesbooks.presentation.navigation.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.tam.tesbooks.R
import com.tam.tesbooks.util.*

@Composable
fun Drawer(
    scaffoldState: ScaffoldState,
    navController: NavHostController
) {

    Column(modifier = Modifier.fillMaxWidth()) {

        Image(
            painter = painterResource(id = R.drawable.tesbooks_banner),
            contentDescription = CONTENT_BANNER,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = PADDING_X_LARGE, horizontal = PADDING_XX_LARGE)

        )
        
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = PADDING_NORMAL)
                .background(color = MaterialTheme.colors.secondaryVariant)
                .padding(vertical = PADDING_NORMAL, horizontal = PADDING_X_LARGE)
                .clickable {  }
        ) {

            Image(
                painter = painterResource(R.drawable.ic_bookmarks_filled),
                contentDescription = CONTENT_BOOKMARKS_ICON,
                modifier = Modifier
                    .padding(end = PADDING_NORMAL)
                    .size(SIZE_ICON_NORMAL)
            )

            Text(
                text = TEXT_BOOKMARKS,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
            )

        }

    }

}