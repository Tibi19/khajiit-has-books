package com.tam.tesbooks.presentation.screen.book

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.presentation.navigation.ARG_BOOK_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var state by mutableStateOf(BookState())
        private set

    init {
        loadBook()
    }

    private fun loadBook() =
        viewModelScope.launch {
            val bookId = savedStateHandle.get<Int>(ARG_BOOK_ID) ?: return@launch
            repository.getBook(bookId)
                .collect { result ->
                    result.onResource(
                        { data -> state = state.copy(book = data) },
                        { error -> state = state.copy(error = error) },
                        { isLoading -> state = state.copy(isLoading = isLoading) }
                    )
                }
        }

    fun onEvent(event: BookEvent) {

    }

}