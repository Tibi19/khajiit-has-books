package com.tam.tesbooks.presentation.navigation.drawer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DrawerViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var state by mutableStateOf(DrawerState())
        private set

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()

    init {
        loadBookLists()
        loadBooksCountInDefaultLists()

        refreshDataObserver(
            viewModelScope = viewModelScope,
            repository = repository,
            refreshBookLists = { loadBookLists() },
            refreshBookSavedInLists = { loadBooksCountInDefaultLists() }
        )
    }

    private fun loadBooksCountInDefaultLists() {
        doOnGettingBookLists { bookLists ->

            val getListLambda: (listName: String) -> BookList? = { listName ->
                bookLists.firstOrNull { it.name == listName }
            }

            listOf(
                getListLambda(DEFAULT_BOOK_LIST_VIEWED),
                getListLambda(DEFAULT_BOOK_LIST_FAVORITE),
                getListLambda(DEFAULT_BOOK_LIST_READ_LATER)
            ).forEach { bookList -> loadBooksCountInList(bookList) }

        }
    }

    private fun loadBooksCountInList(bookList: BookList?) =
        viewModelScope.launch {
            bookList ?: return@launch
            repository.getBookSavesCountInList(bookList)
                .onResource(
                    { data ->
                        data ?: return@onResource
                        val newListNameToBookCountMap = state.defaultListNameToBookCountMap.toMutableMap()
                        newListNameToBookCountMap[bookList.name] = data
                        state = state.copy(defaultListNameToBookCountMap = newListNameToBookCountMap)
                    },
                    { error -> errorChannel.send(error ?: FALLBACK_ERROR_COUNT_BOOK_SAVES) }
                )
        }

    private fun loadBookLists() {
        doOnGettingBookLists { bookLists ->
            state = state.copy(bookLists = bookLists)
        }
    }

    private fun doOnGettingBookLists(onGettingBookLists: (bookLists: List<BookList>) -> Unit) =
        viewModelScope.launch {
            repository.getBookLists()
                .onResource(
                    { data ->
                        data ?: return@onResource
                        onGettingBookLists(data)
                    },
                    { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_BOOK_LISTS) }
                )
        }

    fun onEvent(drawerEvent: DrawerEvent) {
        when(drawerEvent) {
            is DrawerEvent.OnCreateNewList -> createNewList(drawerEvent.name)
            is DrawerEvent.OnDeleteList -> deleteList(drawerEvent.bookList)
            is DrawerEvent.OnRenameList -> renameList(drawerEvent.bookList, drawerEvent.newName)
        }
    }

    private fun renameList(bookList: BookList, newName: String) =
        viewModelScope.launch {
            val newBookList = bookList.copy(name = newName)
            repository.editBookList(newBookList)
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