package com.tam.tesbooks.presentation.screen.book

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.presentation.navigation.ARG_BOOK_ID
import com.tam.tesbooks.util.FALLBACK_ERROR_LOAD_BOOK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var state by mutableStateOf(BookState())
        private set

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()

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
                        { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_BOOK) },
                        { isLoading -> state = state.copy(isLoading = isLoading) }
                    )
                }
        }

    fun onEvent(event: BookEvent) {

    }

}