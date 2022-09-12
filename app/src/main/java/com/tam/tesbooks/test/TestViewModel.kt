package com.tam.tesbooks.test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.data.repository.RepositoryTest
import com.tam.tesbooks.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class TestViewModel @Inject constructor(
    private val repository: RepositoryTest
): ViewModel() {

    var searchQuery by mutableStateOf("")
    var metadata by mutableStateOf(getEmptyMetadataEntity())
    val bookText = MutableStateFlow(getEmptyBookTextDto())

    init {
        saveMetadatas()
    }

    fun search(query: String) {
        searchQuery = query

        if (searchQuery.isEmpty() || searchQuery.toInt() >= SIZE_BOOK_METADATAS) return

        val searchId = searchQuery.toInt()
        viewModelScope.launch {
            metadata = repository.getMetadataFromRoom(searchId)
            bookText.value = repository.getBookText(metadata.fileName)
        }
    }

    fun showTest() {
        printTest("")
    }

    private fun saveMetadatas() =
        viewModelScope.launch {
            repository.saveMetadatas()
        }

}