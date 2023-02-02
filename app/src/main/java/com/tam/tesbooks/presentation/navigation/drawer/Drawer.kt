package com.tam.tesbooks.presentation.navigation.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tam.tesbooks.R
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.ui.theme.CustomColors
import com.tam.tesbooks.ui.theme.KhajiitHasBooksTheme
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
                .clickable { }
                .padding(vertical = PADDING_NORMAL, horizontal = PADDING_X_LARGE)
        ) {

            Image(
                painter = painterResource(R.drawable.ic_bookmarks_filled),
                contentDescription = CONTENT_BOOKMARKS_ICON,
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
                    .clickable { }
            )

        }

        Text(
            text = TEXT_BOOK_LISTS,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            color = CustomColors.colors.onSurfaceVariant,
            modifier = Modifier.padding(start = PADDING_X_LARGE, bottom = PADDING_LARGE)
        )

    }

}

@Preview
@Composable
fun DrawerPreview() =
    KhajiitHasBooksTheme {
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(350.dp),
            color = MaterialTheme.colors.secondary,
            contentColor = Color.White
        ) {
            Drawer(
                scaffoldState = rememberScaffoldState(),
                navController = rememberNavController()
            )
        }
    }
