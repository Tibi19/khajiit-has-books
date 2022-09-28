package com.tam.tesbooks.presentation.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tam.tesbooks.domain.model.listing_modifier.LibraryOrder
import com.tam.tesbooks.domain.model.listing_modifier.Order
import com.tam.tesbooks.util.*

@Preview
@Composable
fun OrderByDialog(
    isOpenState: MutableState<Boolean> = remember{ mutableStateOf(true) },
    libraryOrder: LibraryOrder = LibraryOrder(),
    onSubmitOrder: (newLibraryOrder: LibraryOrder) -> Unit = {}
) {
    if(!isOpenState.value) return

    val newLibraryOrderState = remember { mutableStateOf(libraryOrder) }
    DefaultDialog(
        isOpenState = isOpenState,
        onSubmit = { onSubmitOrder(newLibraryOrderState.value) },
        dialogTitle = TEXT_ORDER_BY_DIALOG_TITLE
    ) {
        OrderByDialogBody(newLibraryOrderState = newLibraryOrderState)
    }
}

@Composable
fun OrderByDialogBody(newLibraryOrderState: MutableState<LibraryOrder>) {
    val radioOptions = Order.values()
    Column {
        radioOptions.forEach { order ->

            val isOrderSelected = order == newLibraryOrderState.value.orderBy

            Row(
                modifier = Modifier.selectable(
                    selected = isOrderSelected,
                    onClick = { selectOrder(newLibraryOrderState, isOrderSelected, order) }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = isOrderSelected,
                    onClick = { selectOrder(newLibraryOrderState, isOrderSelected, order) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colors.primary
                    )
                )
                Text(
                    text = getOrderText(order),
                    modifier = Dimens.PADDING_SMALL
                )
            }

        }

        val isOrderReversed = newLibraryOrderState.value.isReversed
        Row(
            modifier = Modifier.selectable(
                selected = isOrderReversed,
                onClick = { switchIsOrderReversed(newLibraryOrderState, isOrderReversed) }
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = isOrderReversed,
                onCheckedChange = { switchIsOrderReversed(newLibraryOrderState, isOrderReversed) },
                colors = SwitchDefaults.colors(
                    uncheckedThumbColor = MaterialTheme.colors.onSecondary,
                    checkedThumbColor = MaterialTheme.colors.primary,
                    checkedTrackColor = MaterialTheme.colors.primary
                )
            )
            Text(
                text = TEXT_REVERSE_ORDER,
                modifier = Dimens.PADDING_SMALL
            )
        }
    }
}

private fun selectOrder(
    libraryOrderState: MutableState<LibraryOrder>,
    isOrderSelected: Boolean,
    order: Order?
) {
    var selectedOrder: Order? = order
    if (isOrderSelected) {
        selectedOrder = null
    }
    val newLibraryOrder = libraryOrderState.value.copy(orderBy = selectedOrder)
    libraryOrderState.value = newLibraryOrder
}

private fun switchIsOrderReversed(
    libraryOrderState: MutableState<LibraryOrder>,
    isOrderReversed: Boolean
) {
    val selectedLibraryOrder = libraryOrderState.value.copy(isReversed = !isOrderReversed)
    libraryOrderState.value = selectedLibraryOrder
}