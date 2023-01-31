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
import androidx.compose.ui.tooling.preview.Preview
import com.skydoves.landscapist.glide.GlideImage
import com.tam.tesbooks.domain.model.book.BookParagraph
import com.tam.tesbooks.domain.model.book.ImageParagraph
import com.tam.tesbooks.domain.model.book.TextParagraph
import com.tam.tesbooks.util.*

@Preview(showBackground = true)
@Composable
fun BookParagraphItem(
    modifier: Modifier = Modifier,
    bookParagraph: BookParagraph = getTestTextParagraph(),
    onLongPressParagraph: (() -> Unit)? = null,
    shouldHighlightBackground: Boolean = false
) {
    val paddingTextVerticalHalf = PADDING_TEXT_VERTICAL / 2
    val paragraphModifier = modifier
        .fillMaxWidth()
        .padding(vertical = paddingTextVerticalHalf)
        .longPressParagraphModifier(
            onLongPressParagraph = onLongPressParagraph,
            shouldHighlightBackground = shouldHighlightBackground,
            highlightColor = MaterialTheme.colors.primary.copy(alpha = BOOKMARK_HIGHLIGHT_ALPHA)
        )
        .padding(vertical = paddingTextVerticalHalf, horizontal = PADDING_TEXT_HORIZONTAL)

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