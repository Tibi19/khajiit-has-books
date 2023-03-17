package com.tam.tesbooks.presentation.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tam.tesbooks.util.PADDING_NORMAL
import com.tam.tesbooks.util.TEXT_SORT_LIST

@Composable
fun SortListDialog(isOpenState: MutableState<Boolean>) {
    if(!isOpenState.value) return

    BottomSheetDialogProvider.showDialog(
        isOpenState = isOpenState,
        content = { SortListDialogContent(isOpenState = isOpenState) }
    )
}

@Composable
private fun SortListDialogContent(isOpenState: MutableState<Boolean>) {
    BottomSheetDialog(
        title = TEXT_SORT_LIST,
        isOpenState = isOpenState,
        onSubmit = {}
    ) {
        Button(onClick = { isOpenState.value = false }) {
            Text(text = "Some text")
        }
        Spacer(modifier = Modifier.size(PADDING_NORMAL))
        Button(onClick = { isOpenState.value = false }) {
            Text(text = "Some other text")
        }
        Spacer(modifier = Modifier.size(PADDING_NORMAL))
        Button(onClick = { isOpenState.value = false }) {
            Text(text = "Some more text")
        }
    }
}