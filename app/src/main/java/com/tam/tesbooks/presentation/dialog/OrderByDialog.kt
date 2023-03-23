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

@Composable
fun OrderByDialog(
    isOpenState: MutableState<Boolean>,
    libraryOrder: LibraryOrder,
    onSubmitOrder: (newLibraryOrder: LibraryOrder) -> Unit
) {
    if(!isOpenState.value) return

    BottomSheetDialogProvider.showDialog(
        isOpenState = isOpenState,
        content = {
            OrderByDialogContent(
                isOpenState = isOpenState,
                libraryOrder = libraryOrder,
                onSubmitOrder = { newOrder -> onSubmitOrder(newOrder) }
            )
        }
    )

}

@Composable
private fun OrderByDialogContent(
    isOpenState: MutableState<Boolean>,
    libraryOrder: LibraryOrder,
    onSubmitOrder: (newOrder: LibraryOrder) -> Unit
) {
    val libraryOrderState = remember { mutableStateOf(libraryOrder) }
    BottomSheetDialog(
        title = TEXT_ORDER_BY_DIALOG_TITLE,
        isOpenState = isOpenState,
        onSubmit = { onSubmitOrder(libraryOrderState.value) }
    ) {
        val options = Order.values()
        options.forEach { order ->
            val isOrderSelected = order == libraryOrderState.value.orderBy
            RadioOption(
                text = getOrderText(order),
                isSelected = isOrderSelected,
                onSelect = { selectOrder(libraryOrderState, isOrderSelected, order) },
                modifier = Modifier.padding(bottom = PADDING_X_SMALL)
            )
        }

        val isOrderReversed = libraryOrderState.value.isReversed
        SwitchOption(
            text = TEXT_REVERSE_ORDER,
            isChecked = isOrderReversed,
            onChange = { switchIsOrderReversed(libraryOrderState, isOrderReversed) }
        )
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