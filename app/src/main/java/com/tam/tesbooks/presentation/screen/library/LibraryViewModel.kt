package com.tam.tesbooks.presentation.screen.library

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.listing_modifier.LibraryFilter
import com.tam.tesbooks.domain.model.listing_modifier.LibraryOrder
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.presentation.navigation.ARG_CATEGORY
import com.tam.tesbooks.presentation.navigation.ARG_TAG
import com.tam.tesbooks.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    var state by mutableStateOf(LibraryState())
        private set

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()

    init {
        checkForTagFilter()
        checkForCategoryFilter()
        loadBookLists()
        loadBookInfos()

        refreshDataObserver(
            viewModelScope = viewModelScope,
            repository = repository,
            refreshBookSavedInLists = { bookInfoId -> updateBookInfo(bookInfoId) }
        )
    }

    private fun checkForTagFilter() {
        val tag = savedStateHandle.get<String>(ARG_TAG)?.toNormalArg() ?: return
        val tagLibraryFilter = LibraryFilter(tagFilters = listOf(tag))
        state = state.copy(libraryFilter = tagLibraryFilter)
    }

    private fun checkForCategoryFilter() {
        val category = savedStateHandle.get<String>(ARG_CATEGORY)?.toNormalArg() ?: return
        val categoryLibraryFilter = LibraryFilter(categoryFilters = listOf(category))
        state = state.copy(libraryFilter = categoryLibraryFilter)
    }

    private fun loadBookLists() =
        viewModelScope.launch {
            repository
                .getBookLists()
                .onResource(
                    { data ->
                        data ?: return@onResource
                        state = state.copy(bookLists = data)
                    },
                    { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_BOOK_LISTS) }
                )
        }

    private fun loadBookInfos(shouldLoadMore: Boolean = false) =
        viewModelScope.launch {
            getBookInfosFlow(shouldLoadMore)
                .collect { result ->
                    result.onResource(
                        { data ->
                            data ?: return@onResource
                            val newBookInfos = if(shouldLoadMore) state.bookInfos + data else data
                            state = state.copy(bookInfos = newBookInfos)
                        },
                        { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_BOOK_INFOS) },
                        { isLoading -> state = state.copy(isLoading = isLoading) }
                    )
                }
        }

    private suspend fun getBookInfosFlow(shouldLoadMore: Boolean) =
        if (shouldLoadMore)
            repository.getBookInfos(state.libraryOrder, state.libraryFilter, state.searchQuery, state.bookInfos)
        else
            repository.getBookInfos(state.libraryOrder, state.libraryFilter, state.searchQuery)

    fun onEvent(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.OnLoadMoreBookInfos -> loadBookInfos(true)
            is LibraryEvent.OnChangeBookList -> changeBookList(event.bookInfo, event.bookList)
            is LibraryEvent.OnSearchQueryChange -> changeSearchQuery(event.query)
            is LibraryEvent.OnOrderChange -> changeOrder(event.newLibraryOrder)
            is LibraryEvent.OnFilterChange -> changeFilter(event.newLibraryFilter)
        }
    }

    private fun changeBookList(bookInfo: BookInfo, bookList: BookList) =
        viewModelScope.launch {
            val isBookInList = bookInfo.savedInBookLists.any { it.id == bookList.id }

            if(isBookInList) {
                repository.removeBookFromList(bookInfo, bookList)
            } else {
                repository.addBookToList(bookInfo, bookList)
            }
        }

    private fun updateBookInfo(bookInfoId: Int) =
        viewModelScope.launch {
            repository.getBookInfo(bookInfoId)
                .onResource(
                    { data ->
                        data ?: return@onResource
                        val updateAtIndex = state.bookInfos.indexOfFirst { it.bookId == data.bookId }
                        if (updateAtIndex < 0) return@onResource
                        val newBookInfos = state.bookInfos.toMutableList()
                        newBookInfos[updateAtIndex] = data
                        state = state.copy(bookInfos = newBookInfos)
                    },
                    { error -> errorChannel.send(error ?: FALLBACK_ERROR_UPDATE_BOOK_INFO) }
                )
        }

    private var searchJob: Job? = null

    private fun changeSearchQuery(query: String) {
        state = state.copy(searchQuery = query)
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(TIME_WAIT_AFTER_SEARCH_INPUT)
            loadBookInfos()
        }
    }

    private fun changeOrder(newLibraryOrder: LibraryOrder) {
        state = state.copy(libraryOrder = newLibraryOrder)
        loadBookInfos()
    }

    private fun changeFilter(newLibraryFilter: LibraryFilter) {
        state = state.copy(libraryFilter = newLibraryFilter)
        loadBookInfos()
    }

}