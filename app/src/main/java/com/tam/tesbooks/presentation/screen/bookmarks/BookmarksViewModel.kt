package com.tam.tesbooks.presentation.screen.bookmarks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.util.FALLBACK_ERROR_LOAD_BOOKMARKS
import com.tam.tesbooks.util.LIMIT_ROOM_QUERY_DEFAULT
import com.tam.tesbooks.util.TIME_MINIMUM_FOR_LOADING_MORE_ELEMENTS
import com.tam.tesbooks.util.refreshDataObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var state by mutableStateOf(BookmarksState())
        private set

    private val errorChannel = Channel<String>()
    val errorFlow = errorChannel.receiveAsFlow()

    init {
        loadBookmarks()

        refreshDataObserver(
            viewModelScope = viewModelScope,
            repository = repository,
            refreshBookmarks = { reloadBookmarks() }
        )
    }

    private fun reloadBookmarks() =
        viewModelScope.launch {
            val reloadedBookmarks = mutableListOf<Bookmark>()
            repeat(state.pagesLoaded) {
                repository
                    .getBookmarks(lastLoadedBookmark = reloadedBookmarks.lastOrNull())
                    .collect { bookmarksResource ->
                        bookmarksResource.onResource(
                            onSuccess = { bookmarks ->
                                bookmarks ?: return@collect
                                reloadedBookmarks.addAll(bookmarks)
                            },
                            onError = { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_BOOKMARKS) }
                        )
                    }
            }
            val areAllPagesAtMaxBookmarks = reloadedBookmarks.size >= state.pagesLoaded * LIMIT_ROOM_QUERY_DEFAULT
            state = state.copy(
                bookmarks = reloadedBookmarks,
                canLoadMore = areAllPagesAtMaxBookmarks
            )
        }

    private fun loadBookmarks(lastLoadedBookmark: Bookmark? = null) =
        viewModelScope.launch {
            repository
                .getBookmarks(lastLoadedBookmark)
                .collect { result ->
                    result.onResource(
                        { data ->
                            data ?: return@onResource
                            val isBookmarksCountAtMax = data.size >= LIMIT_ROOM_QUERY_DEFAULT
                            val newBookmarks = state.bookmarks + data
                            val newPagesLoaded = state.pagesLoaded + 1
                            state = state.copy(
                                bookmarks = newBookmarks,
                                canLoadMore = isBookmarksCountAtMax,
                                pagesLoaded = newPagesLoaded
                            )
                        },
                        { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_BOOKMARKS) },
                        { isLoading -> state = state.copy(isLoading = isLoading) }
                    )
                }
        }

    fun onEvent(event: BookmarksEvent) {
        when(event) {
            is BookmarksEvent.OnDeleteBookmark -> deleteBookmark(event.bookmark)
            is BookmarksEvent.OnLoadMoreBookmarks -> loadMoreBookmarks()
        }
    }

    private fun loadMoreBookmarks() =
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            delay(TIME_MINIMUM_FOR_LOADING_MORE_ELEMENTS)
            val lastLoadedBookmark = state.bookmarks.last()
            loadBookmarks(lastLoadedBookmark)
        }

    private fun deleteBookmark(bookmark: Bookmark) =
        viewModelScope.launch {
            repository.removeBookmark(bookmark)
        }

}