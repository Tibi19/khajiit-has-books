package com.tam.tesbooks.presentation.screen.book

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.tam.tesbooks.presentation.reusable.BookParagraphItem
import com.tam.tesbooks.presentation.reusable.OnErrorEffect
import com.tam.tesbooks.util.Dimens

@Preview
@Composable
fun BookScreen(bookViewModel: BookViewModel = hiltViewModel()) {

    val state = bookViewModel.state

    OnErrorEffect(errorFlow = bookViewModel.errorFlow)

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        val metadata = state.book?.bookInfo?.metadata ?: return@LazyColumn

        item {
            Spacer(modifier = Dimens.PADDING_DEFAULT)

            Text(
                modifier = Dimens.PADDING_DEFAULT.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                text = metadata.title
            )

            Text(modifier = Dimens.PADDING_TEXT, text = metadata.author)

            val tags = metadata.tags.joinToString(" | ")
            Text(modifier = Dimens.PADDING_TEXT, text = tags)

            Spacer(modifier = Dimens.PADDING_DEFAULT)
        }

        items(state.book.paragraphs) { bookParagraph ->
            BookParagraphItem(
                bookParagraph = bookParagraph
            )
        }

        item {
            Spacer(modifier = Dimens.PADDING_DEFAULT)
        }
    }

}