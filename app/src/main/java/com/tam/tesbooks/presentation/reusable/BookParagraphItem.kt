package com.tam.tesbooks.presentation.reusable

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.tam.tesbooks.domain.model.book.BookParagraph
import com.tam.tesbooks.domain.model.book.ImageParagraph
import com.tam.tesbooks.domain.model.book.TextParagraph
import com.tam.tesbooks.util.HIGHLIGHT_ALPHA_BOOKMARK
import com.tam.tesbooks.util.PADDING_NORMAL
import com.tam.tesbooks.util.PADDING_X_SMALL
import com.tam.tesbooks.util.getTestTextParagraph

@Preview(showBackground = true)
@Composable
fun BookParagraphItem(
    modifier: Modifier = Modifier,
    bookParagraph: BookParagraph = getTestTextParagraph(),
    onLongPressParagraph: (() -> Unit)? = null,
    shouldHighlightBackground: Boolean = false
) {
    val paddingParagraphVerticalHalf = PADDING_X_SMALL / 2
    val paragraphModifier = modifier
        .fillMaxWidth()
        .padding(vertical = paddingParagraphVerticalHalf)
        .longPressParagraphModifier(
            onLongPressParagraph = onLongPressParagraph,
            shouldHighlightBackground = shouldHighlightBackground,
            highlightColor = MaterialTheme.colors.primaryVariant.copy(alpha = HIGHLIGHT_ALPHA_BOOKMARK)
        )
        .padding(vertical = paddingParagraphVerticalHalf, horizontal = PADDING_NORMAL)

    when(bookParagraph) {
        is TextParagraph -> TextParagraphItem(bookParagraph, paragraphModifier)
        is ImageParagraph -> ImageParagraphItem(bookParagraph, paragraphModifier)
    }
}

private fun Modifier.longPressParagraphModifier(
    onLongPressParagraph: (() -> Unit)?,
    shouldHighlightBackground: Boolean,
    highlightColor: Color,
): Modifier =
    onLongPressParagraph?.let {
        this
            .conditional(condition = shouldHighlightBackground) {
                background(color = highlightColor)
            }
            .pointerInput(Unit) {
                detectTapGestures(onLongPress = {
                    onLongPressParagraph()
                })
            }
    } ?: this

@Composable
private fun TextParagraphItem(
    textParagraph: TextParagraph,
    modifier: Modifier
) {
    Text(
        modifier = modifier,
        text = textParagraph.text
    )
}

@Composable
private fun ImageParagraphItem(
    imageParagraph: ImageParagraph,
    modifier: Modifier
) {
    AsyncImage(
        modifier = modifier,
        model = imageParagraph.imageUrl,
        contentScale = ContentScale.FillWidth,
        contentDescription = getContentFromImageUrl(imageParagraph.imageUrl)
    )
}

private fun getContentFromImageUrl(url: String): String {
    val positionLastPartOfUrl = url.lastIndexOf('/')
    val positionImageExtension = url.lastIndexOf('.')
    val imageIdentifier = url.substring(positionLastPartOfUrl..positionImageExtension)
    val positionFirstUnderline = imageIdentifier.indexOf('_')
    val rawDescription = imageIdentifier.substring(positionFirstUnderline + 1)
    return rawDescription.replace('_', ' ')
}