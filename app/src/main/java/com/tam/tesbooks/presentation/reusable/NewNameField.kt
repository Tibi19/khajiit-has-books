package com.tam.tesbooks.presentation.reusable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.tam.tesbooks.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RowScope.NewNameField(
    isActiveState: MutableState<Boolean>,
    confirmationContent: String,
    cancellationContent: String,
    modifier: Modifier = Modifier,
    originalName: String = "",
    bringParentIntoView: suspend () -> Unit = {},
    onNewName: (String) -> Unit
) {
    if(!isActiveState.value) return

    val newNameFieldFocusRequester = remember { FocusRequester() }
    LaunchedEffect(key1 = newNameFieldFocusRequester) {
        newNameFieldFocusRequester.requestFocus()
    }

    var newNameValue by remember {
        val textFieldEndSelection = TextRange(originalName.length)
        val textFieldValue = TextFieldValue(text = originalName, selection = textFieldEndSelection)
        mutableStateOf(textFieldValue)
    }
    val defaultTextColor = TextFieldDefaults.textFieldColors().textColor(true).value
    val newNameInteractionSource = remember { MutableInteractionSource() }
    val indicatorColorState = TextFieldDefaults.textFieldColors().indicatorColor(
        enabled = true,
        isError = false,
        interactionSource = newNameInteractionSource
    )
    val isNewNameFocusedByInteractionSource by newNameInteractionSource.collectIsFocusedAsState()
    val coroutineScope = rememberCoroutineScope()
    val keyboard = LocalSoftwareKeyboardController.current
    val isKeyboardOpenState = isKeyboardOpenAsState()

    BasicTextField(
        value = newNameValue,
        onValueChange = { newNameValue = it },
        singleLine = true,
        cursorBrush = SolidColor(MaterialTheme.colors.primary),
        textStyle = MaterialTheme.typography.h5.copy(color = defaultTextColor),
        interactionSource = newNameInteractionSource,
        decorationBox = { innerTextField ->
            Box(modifier = Modifier.weight(1f)) {
                Row {
                    Row (modifier = Modifier.weight(1f)) { innerTextField() }
                    Spacer(modifier = Modifier.width(PADDING_X_SMALL))
                    NewNameTrailingIcon(
                        newNameValue = newNameValue,
                        originalName = originalName,
                        confirmationContent = confirmationContent,
                        cancellationContent = cancellationContent,
                        isNewNameActiveState = isActiveState,
                        onNewName = onNewName
                    )
                }
                NewNameIndicator(colorState = indicatorColorState)
            }
        },
        modifier = modifier
            .focusRequester(newNameFieldFocusRequester)
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    coroutineScope.launchRaiseKeyboardJob(
                        keyboard = keyboard,
                        isKeyboardOpenState = isKeyboardOpenState,
                        bringIntoView = bringParentIntoView
                    )
                }

                val isTransitioningFromFocusToUnfocus = isNewNameFocusedByInteractionSource && !focusState.isFocused
                if (isTransitioningFromFocusToUnfocus) {
                    isActiveState.value = false
                }
            }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
private fun CoroutineScope.launchRaiseKeyboardJob(
    keyboard: SoftwareKeyboardController?,
    isKeyboardOpenState: State<Boolean>,
    bringIntoView: suspend () -> Unit
) =
    launch {
        do {
            delay(TIME_WAIT_TO_RETRY_SHOWING_KEYBOARD_FOR_NAME_FIELD)
            keyboard?.show() ?: return@launch
        } while (!isKeyboardOpenState.value)

        repeat(TIMES_TO_TRY_BRINGING_INTO_VIEW_NAME_FIELD) {
            awaitFrame()
            bringIntoView()
        }
    }

@Composable
private fun BoxScope.NewNameIndicator(colorState: State<Color>) =
    Box(
        modifier = Modifier
            .offset(y = OFFSET_NEW_NAME_INDICATOR)
            .fillMaxWidth()
            .height(SIZE_NEW_NAME_INDICATOR)
            .background(color = colorState.value)
            .align(Alignment.BottomCenter)
    )

@Composable
private fun NewNameTrailingIcon(
    newNameValue: TextFieldValue,
    originalName: String,
    confirmationContent: String,
    cancellationContent: String,
    isNewNameActiveState: MutableState<Boolean>,
    onNewName: (String) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    if (newNameValue.text.isEmpty() || newNameValue.text == originalName) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = cancellationContent,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.clickable { isNewNameActiveState.value = false }
        )
    } else {
        Icon(
            imageVector = Icons.Default.Done,
            contentDescription = confirmationContent,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.clickable {
                onNewName(newNameValue.text)
                coroutineScope.launch {
                    // onNewName might update state, so we wait for this state and ui to update first
                    // Otherwise, screen will flicker between this and updating isNewNameActiveState
                    delay(TIME_WAIT_FOR_NEW_NAME_UPDATE)
                    isNewNameActiveState.value = false
                }
            }
        )
    }

}
