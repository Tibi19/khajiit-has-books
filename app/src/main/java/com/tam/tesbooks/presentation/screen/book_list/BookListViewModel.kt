package com.tam.tesbooks.presentation.screen.book_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.presentation.navigation.ARG_BOOK_LIST_ID
import com.tam.tesbooks.util.FALLBACK_ERROR_LOAD_BOOK_INFOS
import com.tam.tesbooks.util.FALLBACK_ERROR_LOAD_BOOK_LISTS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var state by mutableStateOf(BookListState())
        private set

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()

    init {
        val bookListId = savedStateHandle.get<Int>(ARG_BOOK_LIST_ID)
        viewModelScope.launch {
            bookListId ?: return@launch
            loadBookLists()
            setupBookList(bookListId)
            loadBookInfos()
        }
    }

    private fun setupBookList(bookListId: Int) {
        val bookList = state.bookLists.find { it.id == bookListId } ?: return
        state = state.copy(bookList = bookList)
    }

    private suspend fun loadBookLists() =
        repository
            .getBookLists()
            .onResource(
                onSuccess = { bookLists ->
                    bookLists ?: return
                    state = state.copy(bookLists = bookLists)
                },
                onError = { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_BOOK_LISTS)  }
            )

    private suspend fun loadBookInfos() {
        val bookList = state.bookList ?: return
        repository
            .getBookInfosFromList(bookList, state.listSort)
            .collect { result ->
                result.onResource(
                    onSuccess = { bookInfos ->
                        bookInfos ?: return@onResource
                        state = state.copy(bookInfos = bookInfos)
                    },
                    onError = { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_BOOK_INFOS) },
                    onLoading = { isLoading -> state = state.copy(isLoading = isLoading) }
                )
            }
    }

    fun onEvent(event: BookListEvent) {
        when(event) {
            is BookListEvent.OnLoadMoreBookInfos -> {}
            is BookListEvent.OnChangeBookList -> {}
            is BookListEvent.OnSortChange -> {}
        }
    }

}