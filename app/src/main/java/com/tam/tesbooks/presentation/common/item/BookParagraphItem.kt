package com.tam.tesbooks.presentation.common.item

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.tam.tesbooks.domain.model.book.BookParagraph
import com.tam.tesbooks.domain.model.book.ImageParagraph
import com.tam.tesbooks.domain.model.book.TextParagraph
import com.tam.tesbooks.ui.theme.EBGaramondFontFamily
import com.tam.tesbooks.util.*

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
            highlightColor = MaterialTheme.colors.primaryVariant.copy(alpha = ALPHA_HIGHLIGHT_BACKGROUND)
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
        val backgroundColor = if (shouldHighlightBackground) highlightColor else Color.Transparent
        this
            .background(backgroundColor)
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
    val textParagraphStyle = getTextParagraphStyle(textParagraph = textParagraph)
    val shouldCenterWithPadding = textParagraphStyle.textAlign == TextAlign.Center || textParagraph.isQuote
    val horizontalPadding = if (shouldCenterWithPadding) PADDING_NORMAL else 0.dp
    Text(
        text = textParagraph.text,
        style = textParagraphStyle,
        modifier = modifier.padding(horizontal = horizontalPadding)
    )
}

private val titleStyle = TextStyle(
    fontFamily = EBGaramondFontFamily,
    fontWeight = FontWeight.Bold,
    textAlign = TextAlign.Center,
    fontSize = SIZE_TEXT_NORMAL
)
private val headerStyle = TextStyle(
    fontFamily = EBGaramondFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = SIZE_TEXT_SMALL
)
private val quoteStyle = TextStyle(
    fontStyle = FontStyle.Italic,
    textAlign = TextAlign.Justify
)

@Composable
private fun getTextParagraphStyle(textParagraph: TextParagraph): TextStyle {
    if(textParagraph.isTitle) return titleStyle
    if(textParagraph.isHeader) return headerStyle
    if(textParagraph.isQuote) return quoteStyle

    val fontWeight = if (textParagraph.isBold) FontWeight.Bold else FontWeight.Normal
    val textAlign = if (textParagraph.isCentered) TextAlign.Center else TextAlign.Justify
    val fontStyle = if (textParagraph.isItalic) FontStyle.Italic else FontStyle.Normal

    return MaterialTheme.typography.body1.copy(
        fontWeight = fontWeight,
        fontStyle = fontStyle,
        textAlign = textAlign
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