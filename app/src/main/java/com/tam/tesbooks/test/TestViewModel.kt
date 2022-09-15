package com.tam.tesbooks.test

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tam.tesbooks.data.repository.RepositoryTest
import com.tam.tesbooks.domain.model.book.BookInfo
import com.tam.tesbooks.domain.model.listing_modifier.LibraryFilter
import com.tam.tesbooks.domain.model.listing_modifier.LibraryOrder
import com.tam.tesbooks.domain.model.listing_modifier.Order
import com.tam.tesbooks.domain.repository.Repository
import com.tam.tesbooks.util.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.system.measureTimeMillis

@HiltViewModel
class TestViewModel @Inject constructor(
    private val repository: Repository,
    private val repositoryTest: RepositoryTest
): ViewModel() {

    var searchQuery by mutableStateOf("")
    var metadata by mutableStateOf(getEmptyMetadataEntity())
    val bookText = MutableStateFlow(getEmptyBookTextDto())

    init {
        initializeDatabase()
    }

    private fun initializeDatabase() =
        viewModelScope.launch {
            repository.initializeDatabase()
                .onResource(
                    { printTest("Database is initialized") },
                    { error -> printTest("Error initializing database: $error") }
                )
        }

    fun showTest() {
        printTest("")
        showBookInfos()
    }

    private fun showBookInfos() =
        viewModelScope.launch {
            delay(3000)
            val libraryOrder = LibraryOrder(Order.CATEGORY)
            val libraryFilter = LibraryFilter(isExcludingAnonymous = false)
            val alreadyLoaded = listOf<BookInfo>()
            val searchQuery = "argonian"
            val bookInfos = repository.getBookInfos(libraryOrder, libraryFilter, alreadyLoaded, searchQuery)

            bookInfos.collect { result ->
                result.onResource(
                    { data -> data?.let { printBookInfosWithLists(data) } },
                    { error -> error?.let { printTest(error) } },
                    { isLoading -> printTest("Is loading boon infos: $isLoading") }
                )
            }
        }

//    private fun showBookInfosNextPage(alreadyLoaded: List<BookInfo>, stopPage: Boolean = false): Job =
//        viewModelScope.launch {
//            printTest("Next page:")
//            val libraryOrder = LibraryOrder(Order.CATEGORY)
//            val libraryFilter = LibraryFilter(isExcludingAnonymous = false)
//            val searchQuery = "argonian"
//            val bookInfos = repository.getBookInfos(libraryOrder, libraryFilter, alreadyLoaded, searchQuery) ?: return@launch
//            printBookInfosWithLists(bookInfos)
//            if(!stopPage){
//                val alreadyLoadedNew = alreadyLoaded + bookInfos
//                showBookInfosNextPage(alreadyLoadedNew, true)
//            }
//        }

//    private suspend fun saveBooksTest(bookInfos: List<BookInfo>) {
//        val bookLists = repository.getBookLists()
//        repository.addBookToList(bookInfos[0], bookLists[0])
//        repository.addBookToList(bookInfos[0], bookLists[1])
//        repository.addBookToList(bookInfos[2], bookLists[2])
//        repository.addBookToList(bookInfos[4], bookLists[0])
//        repository.addBookToList(bookInfos[4], bookLists[2])
//        repository.addBookToList(bookInfos[9], bookLists[0])
//        repository.addBookToList(bookInfos[9], bookLists[1])
//        repository.addBookToList(bookInfos[9], bookLists[2])
//        printTest("Saved books")
//    }

    fun search(query: String) {
        searchQuery = query
        if (searchQuery.isEmpty() || searchQuery.toInt() >= SIZE_BOOK_METADATAS) return
        val searchId = searchQuery.toInt()
        viewModelScope.launch {
            metadata = repositoryTest.getMetadataFromRoom(searchId)
            bookText.value = repositoryTest.getBookText(metadata.fileName)
        }
    }


}