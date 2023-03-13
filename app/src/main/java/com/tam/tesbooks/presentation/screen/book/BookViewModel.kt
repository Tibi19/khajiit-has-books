package com.tam.tesbooks.presentation.screen.book

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.domain.model.book.Book
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.book.BookParagraph
import com.tam.tesbooks.domain.model.book_list.BookList
import com.tam.tesbooks.domain.model.bookmark.Bookmark
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.presentation.navigation.ARG_BOOK_ID
import com.tam.tesbooks.presentation.navigation.ARG_PARAGRAPH_POSITION
import com.tam.tesbooks.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*
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

    private val searchLibraryChannel = Channel<Pair<String, String>>()
    val searchLibraryFlow = searchLibraryChannel.receiveAsFlow()

    private val scrollToParagraphPositionChannel = Channel<Int>()
    val scrollToParagraphPositionFlow = scrollToParagraphPositionChannel.receiveAsFlow()

    init {
        loadBookLists()
        savedStateHandle.get<Int>(ARG_BOOK_ID)?.let { bookId ->
            loadBook(bookId)
            loadBookBookmarks(bookId)
        }

        savedStateHandle.get<Int>(ARG_PARAGRAPH_POSITION)?.let { paragraphPosition ->
            scrollToParagraph(paragraphPosition)
        }

        refreshDataObserver(
            viewModelScope = viewModelScope,
            repository = repository,
            refreshBookSavedInLists = { bookInfo -> updateBookInfo(bookInfo) },
            refreshBookmarks = {
                val currentBook = state.curentBook ?: return@refreshDataObserver
                loadBookBookmarks(currentBook.bookInfo.bookId)
            }
        )
    }

    private fun scrollToParagraph(paragraphPosition: Int) {
        if (paragraphPosition < 0) return
        viewModelScope.launch {
            scrollToParagraphPositionChannel.send(paragraphPosition)
        }
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

    private fun loadBook(bookId: Int) =
        viewModelScope.launch {
            repository.getBook(bookId)
                .collect { result ->
                    result.onResource(
                        { data ->
                            data ?: return@onResource
                            state = state.copy(
                                booksStack = listOf(data),
                                isLoading = false
                            )
                        },
                        { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_BOOK) },
                        { isLoading -> state = state.copy(isLoading = isLoading) }
                    )
                }
        }

    private fun getExistingBooksIdsFromHistory() = state.booksStack.map { it.bookInfo.bookId }

    private fun loadNextRandomBook(existingBooksIds: List<Int> = getExistingBooksIdsFromHistory()) =
        viewModelScope.launch {
            repository.getRandomBook(existingBooksIds)
                .onResource(
                    { data ->
                        data ?: return@onResource
                        val newBooksStack = state.booksStack + data
                        state = state.copy(booksStack = newBooksStack)
                    },
                    { error ->
                        val errorReason = error ?: FALLBACK_ERROR_UNKNOWN
                        val errorMessage = "$ERROR_LOAD_NEXT_BOOK_BECAUSE$errorReason"
                        errorChannel.send(errorMessage)
                    }
                )
        }

    private fun loadBookBookmarks(bookId: Int) =
        viewModelScope.launch {
            repository.getBookmarksOfBookId(bookId)
                .collect { result ->
                    result.onResource(
                        { data ->
                            data ?: return@onResource
                            state = state.copy(bookmarks = data)
                        },
                        { error -> errorChannel.send(error ?: FALLBACK_ERROR_LOAD_BOOKMARKS) }
                    )
                }
        }

    fun onEvent(event: BookEvent) {
        when(event) {
            is BookEvent.OnTagSearch -> searchTag(event.tag)
            is BookEvent.OnBookSwipe -> {
                state = state.copy(currentBookIndex = event.swipedToBookIndex)
                val swipedToBook = state.curentBook ?: return
                loadBookBookmarks(swipedToBook.bookInfo.bookId)
                loadRandomBookOnSwipingToLastBook(swipedToBook)
            }
            is BookEvent.OnChangeBookList -> changeBookList(event.bookInfo, event.newBookList)
            is BookEvent.OnChangeBookmark -> changeBookmark(event.paragraph)
        }
    }

    private fun changeBookmark(paragraph: BookParagraph) {
        val bookmark = state.bookmarks.find{ it.paragraph == paragraph }
        val isParagraphBookmarked = bookmark != null
        if (isParagraphBookmarked) {
            removeBookmark(bookmark!!)
        } else {
            addBookmark(paragraph)
        }
    }

    private fun removeBookmark(bookmark: Bookmark) =
        viewModelScope.launch {
            repository.removeBookmark(bookmark)
        }

    private fun addBookmark(paragraph: BookParagraph) =
        viewModelScope.launch {
            val currentBook = state.curentBook ?: return@launch
            val bookTitle = currentBook.bookInfo.metadata.title
            val bookmark = Bookmark(
                uuid = UUID.randomUUID(),
                paragraph = paragraph,
                bookTitle = bookTitle,
                dateTimeAdded = LocalDateTime.now()
            )
            repository.addBookmark(bookmark)
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

    private fun updateBookInfo(bookInfo: BookInfo) =
        viewModelScope.launch {
            repository.getBookInfo(bookInfo.bookId)
                .onResource(
                    { data ->
                        data ?: return@onResource
                        val updateAtIndex = state.booksStack.indexOfFirst { it.bookInfo.bookId == data.bookId }
                        val newBook = state.booksStack[updateAtIndex].copy(bookInfo = data)
                        val newBooksStack = state.booksStack.toMutableList()
                        newBooksStack[updateAtIndex] = newBook
                        state = state.copy(booksStack = newBooksStack)
                    },
                    { error -> errorChannel.send(error ?: FALLBACK_ERROR_UPDATE_BOOK_INFO) }
                )
        }

    private fun loadRandomBookOnSwipingToLastBook(swipedToBook: Book) {
        val isSwipedToBookLastInStack = swipedToBook == state.booksStack.last()
        if(!isSwipedToBookLastInStack) return
        loadNextRandomBook()
    }

    private fun searchTag(tag: String) =
        viewModelScope.launch {
            val matchingCategoryToSearch = getMatchingCategoryOfTag(tag)
            val tagToSearch = if (matchingCategoryToSearch.isEmpty()) tag else ""
            val searchPair = Pair(tagToSearch, matchingCategoryToSearch)
            searchLibraryChannel.send(searchPair)
        }

    private suspend fun getMatchingCategoryOfTag(tag: String): String {
        repository.getCategories()
            .onSuccess { categories ->
                return categories?.find { it == tag } ?: ""
            }
        return ""
    }

}