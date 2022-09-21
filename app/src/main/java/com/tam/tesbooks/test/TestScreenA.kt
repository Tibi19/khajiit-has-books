package com.tam.tesbooks.test

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun TestScreenA(navigateToBook: (bookId: Int) -> Unit) {

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var text by remember { mutableStateOf(TextFieldValue("")) }

        TextField(
            value = text,
            modifier = Modifier.width(120.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { text = it }
        )

        Spacer(Modifier.size(10.dp))

        Button(onClick = {
            val bookIdInput = text.text
            val bookId = if (bookIdInput.isEmpty()) 0 else bookIdInput.toInt()
            navigateToBook(bookId)
        }) {
            Text(text = "Go to Book")
        }

    }

}