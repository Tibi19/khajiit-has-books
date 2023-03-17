package com.tam.tesbooks.presentation.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
       Button(onClick = { isOpenState.value = false }) {
           Text(text = "Close Dialog")
       }
    }
}