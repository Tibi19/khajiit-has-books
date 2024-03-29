package com.tam.tesbooks.presentation.screen.book_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.listing_modifier.BookListSort
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.presentation.navigation.ARG_BOOK_LIST_ID
import com.tam.tesbooks.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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

        refreshDataObserver(
            viewModelScope = viewModelScope,
            repository = repository,
            refreshBookSavedInLists = { bookInfoId -> updateBookInfo(bookInfoId) }
        )
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

    private suspend fun loadBookInfos(
        alreadyLoadedInfos: List<BookInfo> = emptyList(),
        shouldReset: Boolean = false
    ) {
        val bookList = state.bookList ?: return
        repository
            .getBookInfosFromList(
                bookList = bookList,
                bookListSort = state.listSort,
                alreadyLoadedInfos = alreadyLoadedInfos
            )
            .collect { result ->
                result.onResource(
                    onSuccess = { bookInfos ->
                        bookInfos ?: return@onResource
                        val isBookInfosCountAtMax = bookInfos.size >= LIMIT_ROOM_QUERY_DEFAULT
                        val newBookInfos = if (shouldReset) bookInfos else state.bookInfos + bookInfos
                        val newPagesLoaded = state.pagesLoaded + 1
                        state = state.copy(
                            bookInfos = newBookInfos,
                            canLoadMore = isBookInfosCountAtMax,
                            pagesLoaded = newPagesLoaded
                        )
                    },
                    onError = { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_BOOK_INFOS) },
                    onLoading = { isLoading -> state = state.copy(isLoading = isLoading) }
                )
            }
    }

    private fun updateBookInfo(bookInfoId: Int) =
        viewModelScope.launch {
            repository.getBookInfo(bookInfoId)
                .onResource(
                    onSuccess = { updatedBookInfo ->
                        updatedBookInfo ?: return@onResource

                        if(didBookInfoChangeScreenList(updatedBookInfo)) {
                            reloadBookInfos()
                            return@onResource
                        }

                        val updateAtIndex = state.bookInfos.indexOfFirst { it.bookId == updatedBookInfo.bookId }
                        if (updateAtIndex < 0) return@onResource
                        val newBookInfos = state.bookInfos.toMutableList()
                        newBookInfos[updateAtIndex] = updatedBookInfo
                        state = state.copy(bookInfos = newBookInfos)
                    },
                    onError = { error -> errorChannel.send(error ?: FALLBACK_ERROR_UPDATE_BOOK_INFO) }
                )
        }

    private fun reloadBookInfos() =
        viewModelScope.launch {
            val bookList = state.bookList ?: return@launch
            val reloadedBookInfos = mutableListOf<BookInfo>()
            repeat(state.pagesLoaded) {
                repository
                    .getBookInfosFromList(
                        bookList = bookList,
                        bookListSort = state.listSort,
                        alreadyLoadedInfos = reloadedBookInfos
                    )
                    .collect { bookInfosResource ->
                        bookInfosResource.onResource(
                            onSuccess = { bookInfos ->
                                bookInfos ?: return@collect
                                reloadedBookInfos.addAll(bookInfos)
                            },
                            onError = { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_BOOK_INFOS) }
                        )
                    }
            }
            val areAllPagesAtMaxBookInfos = reloadedBookInfos.size >= state.pagesLoaded * LIMIT_ROOM_QUERY_DEFAULT
            state = state.copy(
                bookInfos = reloadedBookInfos,
                canLoadMore = areAllPagesAtMaxBookInfos
            )
        }

    private fun didBookInfoChangeScreenList(newBookInfo: BookInfo): Boolean {
        val screenList = state.bookList ?: return false
        val newBookInfoList = newBookInfo.savedInBookLists.find { it.id == screenList.id }
        return newBookInfoList == null
    }

    fun onEvent(event: BookListEvent) {
        when(event) {
            is BookListEvent.OnLoadMoreBookInfos -> loadMoreBookInfos()
            is BookListEvent.OnChangeBookList -> changeBookList(event.bookInfo, event.bookList)
            is BookListEvent.OnSortChange -> changeSort(event.bookListSort)
        }
    }

    private fun loadMoreBookInfos() =
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            delay(TIME_MINIMUM_FOR_LOADING_MORE_ELEMENTS)
            loadBookInfos(alreadyLoadedInfos = state.bookInfos)
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

    private fun changeSort(bookListSort: BookListSort) {
        state = state.copy(listSort = bookListSort)
        viewModelScope.launch { loadBookInfos(shouldReset = true) }
    }

}