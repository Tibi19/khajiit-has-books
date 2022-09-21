package com.tam.tesbooks.presentation.reusable

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.skydoves.landscapist.glide.GlideImage
import com.tam.tesbooks.domain.model.book.BookParagraph
import com.tam.tesbooks.domain.model.book.ImageParagraph
import com.tam.tesbooks.domain.model.book.TextParagraph
import com.tam.tesbooks.util.Dimens
import com.tam.tesbooks.util.getTestTextParagraph

@Preview(showBackground = true)
@Composable
fun BookParagraphItem(
    bookParagraph: BookParagraph = getTestTextParagraph(),
    modifier: Modifier = Dimens.PADDING_TEXT
) {
    when(bookParagraph) {
        is TextParagraph -> TextParagraphItem(bookParagraph, modifier)
        is ImageParagraph -> ImageParagraphItem(bookParagraph, modifier)
    }
}

@Composable
private fun TextParagraphItem(
    textParagraph: TextParagraph,
    modifier: Modifier
) {
    Text(
        modifier = modifier,
        text = textParagraph.text,
        lineHeight = Dimens.SPACING_TEXT_DEFAULT
    )
}

@Composable
private fun ImageParagraphItem(
    imageParagraph: ImageParagraph,
    modifier: Modifier
) {
    GlideImage(
        modifier = modifier,
        imageModel = imageParagraph.imageUrl,
    )
}