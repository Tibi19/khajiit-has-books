package com.tam.tesbooks.presentation.dialog.lists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.util.FALLBACK_ERROR_LOAD_BOOK_LISTS
import com.tam.tesbooks.util.refreshDataObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var state by mutableStateOf(ListsState())
        private set

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()

    init {
        loadBookLists()

        refreshDataObserver(
            viewModelScope = viewModelScope,
            repository = repository,
            refreshBookLists = { loadBookLists() }
        )
    }

    private fun loadBookLists() =
        viewModelScope.launch {
            repository.getBookLists()
                .onResource(
                    { data ->
                        data ?: return@onResource
                        state = state.copy(bookLists = data)
                    },
                    { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_BOOK_LISTS) }
                )
        }

    fun onEvent(listsEvent: ListsEvent) {
        when(listsEvent) {
            is ListsEvent.OnCreateNewList -> createNewList(listsEvent.name)
            is ListsEvent.OnDeleteList -> deleteList(listsEvent.bookList)
        }
    }

    private fun deleteList(bookList: BookList) =
        viewModelScope.launch {
            repository.removeBookList(bookList)
        }

    private fun createNewList(name: String) =
        viewModelScope.launch {
            val newBookList = BookList(name)
            repository.addBookList(newBookList)
        }

}