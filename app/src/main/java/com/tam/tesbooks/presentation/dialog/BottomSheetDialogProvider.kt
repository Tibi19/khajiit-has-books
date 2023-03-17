package com.tam.tesbooks.presentation.dialog

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.tam.tesbooks.util.SIZE_BOTTOM_SHEET_DIALOG_LOADING_INDICATOR
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private data class DialogProvision(
    val isOpenState: MutableState<Boolean>,
    val content: @Composable () -> Unit
)

@OptIn(ExperimentalMaterialApi::class)
object BottomSheetDialogProvider {

    private var bottomSheetState: ModalBottomSheetState? = null
    private var showingProvision: DialogProvision? = null
    private var visibilityJob: Job? = null
    private val isShowing get() = showingProvision != null
    private lateinit var coroutineScope: CoroutineScope

    fun isValueChanging(sheetValue: ModalBottomSheetValue): Boolean {
        if(visibilityJob?.isActive == true) return false
        return sheetValue != ModalBottomSheetValue.HalfExpanded
    }

    @Composable
    fun BottomSheetDialog(bottomSheetState: ModalBottomSheetState) {
        setupBottomSheetState(bottomSheetState)
        coroutineScope = rememberCoroutineScope()
        val isBottomSheetVisible by remember {
            derivedStateOf { bottomSheetState.isVisible }
        }

        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        val paddingToCenterLoadingCircleInHalfSheet = screenHeight / 4 - SIZE_BOTTOM_SHEET_DIALOG_LOADING_INDICATOR / 2

        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            showingProvision?.let { provision ->
                provision.content()
                updateStateOnClosedSheet(isBottomSheetVisible, provision.isOpenState)
            } ?: CircularProgressIndicator(
                modifier = Modifier
                    .size(SIZE_BOTTOM_SHEET_DIALOG_LOADING_INDICATOR)
                    .padding(top = paddingToCenterLoadingCircleInHalfSheet)
            )
        }

        coroutineScope.hideOnClosedState()
    }

    private fun setupBottomSheetState(bottomSheetState: ModalBottomSheetState) {
        this.bottomSheetState ?: run {
            this.bottomSheetState = bottomSheetState
        }
    }

    private fun updateStateOnClosedSheet(
        isBottomSheetVisible: Boolean,
        stateToUpdate: MutableState<Boolean>
    ) {
        val shouldUpdateStateAfterSheetWasClosed = !isBottomSheetVisible && isShowing
        if (shouldUpdateStateAfterSheetWasClosed) {
            stateToUpdate.value = false
            showingProvision = null
        }
    }

    private fun CoroutineScope.show(dialogProvision: DialogProvision) {
        if (isShowing) return
        visibilityJob = launch {
            showingProvision = dialogProvision
            bottomSheetState?.show()
        }
    }

    private fun CoroutineScope.hide() {
        if (!isShowing) return
        visibilityJob = launch {
            bottomSheetState?.hide()
            showingProvision = null
        }
    }

    private fun CoroutineScope.hideOnClosedState() {
        if (!isShowing) return
        showingProvision?.let { provision ->
            if(!provision.isOpenState.value) {
                hide()
            }
        }
    }

    fun showDialog(
        isOpenState: MutableState<Boolean>,
        content: @Composable () -> Unit
    ) {
        if(isShowing) return
        val newDialogProvision = DialogProvision(
            isOpenState = isOpenState,
            content = content
        )
        coroutineScope.show(newDialogProvision)
    }

}