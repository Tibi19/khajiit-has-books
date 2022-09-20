package com.tam.tesbooks.test

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun TestBookScreen(
    viewModel: TestViewModel = hiltViewModel()
) {
    val metadataState = viewModel.metadata
    val bookTextState = viewModel.bookText.collectAsState()

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.search(it) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search book by id...") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1,
            singleLine = true
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxSize()
        ) {
            item {
                if(metadataState.id < 0) return@item

                Text("Title: ${metadataState.title}")
                Text("Author: ${metadataState.author}")
                Text("Description: ${metadataState.description}")
                Text("Category: ${metadataState.category}")
                Divider(modifier = Modifier.padding(10.dp))
            }

            items(bookTextState.value.paragraphs.size) { i ->
                val paragraph = bookTextState.value.paragraphs[i]
                Text(
                    text = paragraph,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}