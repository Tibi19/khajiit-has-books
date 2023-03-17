package com.tam.tesbooks.presentation.dialog

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.tam.tesbooks.R
import com.tam.tesbooks.presentation.reusable.BarRow
import com.tam.tesbooks.presentation.reusable.DefaultTextButton
import com.tam.tesbooks.presentation.reusable.DialogControl
import com.tam.tesbooks.presentation.reusable.SectionText
import com.tam.tesbooks.util.*

@Composable
fun BottomSheetDialog(
    title: String,
    isOpenState: MutableState<Boolean>,
    onSubmit: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val scrollState = rememberScrollState()
    Column {
        Header(
            title = title,
            onSubmit = {
                onSubmit()
                isOpenState.value = false
            },
            onCancel = { isOpenState.value = false }
        )
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(vertical = PADDING_LARGE, horizontal = PADDING_NORMAL)
        ) {
            content()
        }
    }
}

@Composable
private fun Header(
    title: String,
    onSubmit: () -> Unit,
    onCancel: () -> Unit
) =
    BarRow(paddingInside =
        PaddingValues(
            start = PADDING_NORMAL,
            top = PADDING_XX_SMALL,
            bottom = PADDING_XX_SMALL,
            end = PADDING_SMALL
        )
    ) {
        SectionText(
            text = title,
            style = MaterialTheme.typography.h6
        )

        DialogControl(
            confirmText = TEXT_SUBMIT,
            cancelText = TEXT_CANCEL,
            textStyle = MaterialTheme.typography.subtitle1,
            onCancel = onCancel,
            onSubmit = onSubmit
        )
    }
