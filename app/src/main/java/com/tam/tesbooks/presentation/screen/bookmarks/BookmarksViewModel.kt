package com.tam.tesbooks.presentation.screen.bookmarks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.util.FALLBACK_ERROR_LOAD_BOOKMARKS
import com.tam.tesbooks.util.refreshDataObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
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
            refreshBookmarks = { loadBookmarks() }
        )
    }

    private fun loadBookmarks() =
        viewModelScope.launch {
            repository
                .getBookmarks()
                .collect { result ->
                    result.onResource(
                        { data ->
                            data ?: return@onResource
                            state = state.copy(bookmarks = data)
                        },
                        { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_BOOKMARKS) },
                        { isLoading -> state = state.copy(isLoading = isLoading) }
                    )
                }
        }

    fun onEvent(event: BookmarksEvent) {
        when(event) {
            is BookmarksEvent.OnDeleteBookmark -> deleteBookmark(event.bookmark)
        }
    }

    private fun deleteBookmark(bookmark: Bookmark) =
        viewModelScope.launch {
            repository.removeBookmark(bookmark)
        }

}